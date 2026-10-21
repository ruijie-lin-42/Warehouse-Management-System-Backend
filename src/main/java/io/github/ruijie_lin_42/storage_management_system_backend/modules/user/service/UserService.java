package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.ResultCode;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Role;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.AuthorizationException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.BusinessException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.DataIntegrityException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.utils.SecurityUtils;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.dto.UserAuthDTO;
import io.github.ruijie_lin_42.storage_management_system_backend.common.converter.PageConverter;
import io.github.ruijie_lin_42.storage_management_system_backend.common.response.PageResultResponse;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.convert.UserConverter;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.ProfileEditUserRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.entity.User;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.CreateUserRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.DashboardEditUserRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.UserQueryRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.response.UserQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

/**
 * <p>
 * user service implementation
 * </p>
 *
 * @author ruijie-lin-42
 * @since 2026-05-24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    public void createUser(CreateUserRequest createUserRequest) {
        Role operatorRole = SecurityUtils.getCurrentUserHighestRole();
        if (canOperate(operatorRole, createUserRequest.getRole())) {
            User user = userConverter.toEntity(createUserRequest, passwordEncoder.encode((createUserRequest.getPassword())));
            try {
                userMapper.insert(user);
                // TODO: 设置回填id，不然这里拿不到id
                log.info("User created successfully, operatorId={}, userId={}, username={}",
                        SecurityUtils.getUserIdFromContext(), user.getId(), user.getUsername());
            } catch (DuplicateKeyException e) {
                throw new BusinessException(ResultCode.DUPLICATE_USERNAME, Map.of(
                        "username", createUserRequest.getUsername()
                ));
            }
        } else {
            throw new AuthorizationException(ResultCode.INSUFFICIENT_PRIVILEGE, "createUser", Map.of(
                    "targetUsername", createUserRequest.getUsername(),
                    "targetRole", createUserRequest.getRole()
            ));
        }
    }

    public void dashboardEditUserById(DashboardEditUserRequest dashboardEditUserRequest, Long id) {
        User user = userConverter.toEntity(dashboardEditUserRequest);
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, id)
                .isNull(User::getDeletedAt);
        User target = userMapper.selectOne(lambdaUpdateWrapper);
        Role operatorRole = SecurityUtils.getCurrentUserHighestRole();
        if (canOperate(operatorRole, target.getRole())) {
            int affectedNumRows = userMapper.update(user, lambdaUpdateWrapper);
            if (affectedNumRows == 0) {
                throw new BusinessException(ResultCode.USER_UNAVAILABLE, Map.of(
                        "userId", id
                ));
            } else if (affectedNumRows > 1) {
                throw new DataIntegrityException(ResultCode.UPDATE_AFFECTED_ROWS_INVALID, 1, affectedNumRows, "dashboardEditUserById", Map.of(
                        "userId", id
                ));
            }
            log.info("User updated successfully through dashboard, operatorId={}, userId={}, newValues={}",
                    SecurityUtils.getUserIdFromContext(), id, dashboardEditUserRequest);
        } else {
            throw new AuthorizationException(ResultCode.INSUFFICIENT_PRIVILEGE, "dashboardEditUser", Map.of(
                    "targetUserId", target.getId(),
                    "targetUserRole", target.getRole()
            ));
        }
    }

    public void profileEditUserById(ProfileEditUserRequest profileEditUserRequest, Long id) {
        User user = userConverter.toEntity(profileEditUserRequest);
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, id)
                .isNull(User::getDeletedAt);
        int affectedRows = userMapper.update(user, lambdaUpdateWrapper);
        if (affectedRows == 0) {
            throw new BusinessException(ResultCode.USER_UNAVAILABLE, Map.of(
                    "userId", id
            ));
        } else if (affectedRows > 1) {
            throw new DataIntegrityException(ResultCode.UPDATE_AFFECTED_ROWS_INVALID, 1, affectedRows, "profileEditUserById", Map.of(
                    "userId", id
            ));
        }
        log.info("User updated successfully through profile, operatorId={}, userId={}. newValues={}",
                SecurityUtils.getUserIdFromContext(), id, profileEditUserRequest);
    }

    public void deleteUserById(Long id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, id);
        User target = userMapper.selectOne(lambdaQueryWrapper);
        if (target == null) return;
        Role operatorRole = SecurityUtils.getCurrentUserHighestRole();
        if (canOperate(operatorRole, target.getRole())) {
            User user = new User();
            user.setId(id);
            user.setDeletedAt(Instant.now());
            int affectedNumRows = userMapper.updateById(user);
            if (affectedNumRows > 1) {
                throw new DataIntegrityException(ResultCode.DELETE_AFFECTED_ROWS_INVALID, 1, affectedNumRows, "deleteUserById", Map.of(
                        "userId", id
                ));
            }
            log.info("User soft-deleted successfully, operatorId={}, userId={}",
                    SecurityUtils.getUserIdFromContext(), id);
        } else {
            throw new AuthorizationException(ResultCode.INSUFFICIENT_PRIVILEGE, "deleteUserById", Map.of(
                    "targetUserId", target.getId(),
                    "targetUserRole", target.getRole()
            ));
        }
    }

    public boolean ifUsernameExists(String username) {
        return userMapper.existsByUsername(username);
    }

    public PageResultResponse<UserQueryResponse> queryUserInPage(UserQueryRequest userQueryRequest) {
        Page<User> page = new Page<>(userQueryRequest.getPageQueryRequest().getPageNum(), userQueryRequest.getPageQueryRequest().getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, userQueryRequest.getName())
                .isNull(User::getDeletedAt);
        Page<User> result = userMapper.selectPage(page, lambdaQueryWrapper);
        return PageConverter.convert(result, userConverter::toQueryResponse);
    }

    public UserQueryResponse findUserByCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);
        return userConverter.toQueryResponse(user);
    }

    public void changePasswordById(Long id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        int affectedRows = userMapper.updateById(user);
        if (affectedRows > 1) {
            throw new DataIntegrityException(ResultCode.UPDATE_AFFECTED_ROWS_INVALID, 1, affectedRows, "changePasswordById", Map.of(
                    "userId", id
            ));
        } else if (affectedRows == 0) {
            throw new BusinessException(ResultCode.USER_UNAVAILABLE, Map.of(
                    "userId", id
            ));
        }
        log.info("Password changed successfully, operatorId={}, userId={}",
                SecurityUtils.getUserIdFromContext(), id);
    }

    // ==================== for AUTH module ====================

    public UserAuthDTO findAuthInfoByUsername(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username)
                .isNull(User::getDeletedAt);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user == null) return null;
        return userConverter.toAutoDTO(user);
    }

    public UserAuthDTO findAuthInfoByUserId(Long userId) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, userId)
                .isNull(User::getDeletedAt);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user == null) return null;
        return userConverter.toAutoDTO(user);
    }

    public boolean canOperate(Role operator, Role target) {
        return operator != null && operator.hasHigherRoleThan(target);
    }
}
