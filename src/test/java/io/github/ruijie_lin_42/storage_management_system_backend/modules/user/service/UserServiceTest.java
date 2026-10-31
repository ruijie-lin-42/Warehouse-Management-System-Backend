package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.ResultCode;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Role;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Status;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.AuthorizationException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.BusinessException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.DataIntegrityException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.exceptions.UserIdNotFoundException;
import io.github.ruijie_lin_42.storage_management_system_backend.common.request.PageQueryRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.common.response.PageResultResponse;
import io.github.ruijie_lin_42.storage_management_system_backend.common.utils.SecurityUtils;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.convert.UserConverter;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.mapper.UserMapper;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.dto.UserAuthDTO;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.entity.User;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.CreateUserRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.DashboardEditUserRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.ProfileEditUserRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request.UserQueryRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.response.UserQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserConverter userConverter = Mappers.getMapper(UserConverter.class);

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userMapper, passwordEncoder, userConverter);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    class CreateUser {

        @ParameterizedTest
        @CsvSource({"SUPER_ADMIN, ADMIN", "SUPER_ADMIN, USER", "ADMIN, USER"})
        void createUser_shouldSuccessDoNothing_whenCanOperateAndCreateNewUser(Role currentRole, Role targetRole) {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(currentRole);
                CreateUserRequest createUserRequest = getTestCaseGoodRequest(targetRole);
                when(userMapper.insert(any(User.class))).thenReturn(1);

                userService.createUser(createUserRequest);

                ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
                verify(userMapper).insert(captor.capture());
                assertThat(captor.getValue().getRole()).isEqualTo(targetRole);
            }
        }

        @Test
        void createUser_shouldThrowDuplicateKeyException_whenUsernameDuplicates() {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(Role.SUPER_ADMIN);
                CreateUserRequest createUserRequest = getTestCaseGoodRequest(Role.ADMIN);
                when(userMapper.insert(any(User.class))).thenThrow(DuplicateKeyException.class);

                BusinessException exception = assertThrows(BusinessException.class, () -> userService.createUser(createUserRequest));
                Map<String, Object> context = Map.of("username", createUserRequest.getUsername());
                assertThat(exception.getResultCode()).isEqualTo(ResultCode.DUPLICATE_USERNAME);
                assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

        @ParameterizedTest
        @CsvSource({"ADMIN, ADMIN", "SUPER_ADMIN, SUPER_ADMIN", "ADMIN, SUPER_ADMIN"})
        void createUser_shouldThrowAuthorizationException_whenExceedingRole(Role currentRole, Role targetRole) {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(currentRole);
                CreateUserRequest createUserRequest = getTestCaseGoodRequest(targetRole);

                AuthorizationException exception = assertThrows(AuthorizationException.class, () -> userService.createUser(createUserRequest));
                Map<String, Object> context = Map.of("targetUsername", createUserRequest.getUsername(), "targetRole", createUserRequest.getRole());
                assertThat(exception.getResultCode()).isEqualTo(ResultCode.INSUFFICIENT_PRIVILEGE);
                assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

        private CreateUserRequest getTestCaseGoodRequest(Role role) {
            return CreateUserRequest.builder()
                    .username("username123").name("name456").password("password12345678")
                    .age(1).gender("male").email("123@gmail.com").role(role)
                    .build();
        }

    }

    @Nested
    class DashboardEditUserById {

        @ParameterizedTest
        @CsvSource({"SUPER_ADMIN, ADMIN", "SUPER_ADMIN, USER", "ADMIN, USER"})
        void dashboardEditUserById_shouldSuccessDoNothing_whenCanOperate(Role currentRole, Role targetRole) {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(currentRole);
                User targetUser = getTestCaseGoodUser(targetRole);
                DashboardEditUserRequest request = getTestCaseGoodRequest();
                when(userMapper.selectOne(any())).thenReturn(targetUser);
                when(userMapper.update(any(User.class), any())).thenReturn(1);

                userService.dashboardEditUserById(request, targetUser.getId());

                verify(userMapper).selectOne(any());
                verify(userMapper).update(any(User.class), any());
            }
        }

        @Test
        void dashboardEditUserById_shouldThrowBusinessException_whenUserNotFound() {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(Role.SUPER_ADMIN);
                DashboardEditUserRequest request = getTestCaseGoodRequest();
                User targetUser = getTestCaseGoodUser(Role.USER);
                when(userMapper.selectOne(any())).thenReturn(targetUser);
                when(userMapper.update(any(User.class), any())).thenReturn(0);

                BusinessException exception = assertThrows(BusinessException.class, () -> userService.dashboardEditUserById(request, targetUser.getId()));
                Map<String, Object> context = Map.of("userId", targetUser.getId());
                assertThat(exception.getResultCode()).isEqualTo(ResultCode.USER_UNAVAILABLE);
                assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

        @Test
        void dashboardEditUserById_shouldThrowDataIntegrityException_whenMultipleRowsModified() {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(Role.SUPER_ADMIN);
                DashboardEditUserRequest request = getTestCaseGoodRequest();
                User targetUser = getTestCaseGoodUser(Role.USER);
                when(userMapper.selectOne(any())).thenReturn(targetUser);
                when(userMapper.update(any(User.class), any())).thenReturn(2);

                DataIntegrityException exception = assertThrows(DataIntegrityException.class, () -> userService.dashboardEditUserById(request, targetUser.getId()));
                Map<String, Object> context = Map.of("userId", targetUser.getId());
                assertThat(exception.getResultCode()).isEqualTo(ResultCode.UPDATE_AFFECTED_ROWS_INVALID);
                assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

        @ParameterizedTest
        @CsvSource({"ADMIN, ADMIN", "SUPER_ADMIN, SUPER_ADMIN", "ADMIN, SUPER_ADMIN"})
        void dashboardEditUserById_shouldThrowAuthorizationException_whenLackPrivilege(Role currentRole, Role targetRole) {
            try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(currentRole);
                DashboardEditUserRequest request = getTestCaseGoodRequest();
                User targetUser = getTestCaseGoodUser(targetRole);
                when(userMapper.selectOne(any())).thenReturn(targetUser);

                AuthorizationException exception = assertThrows(AuthorizationException.class, () -> userService.dashboardEditUserById(request, targetUser.getId()));
                Map<String, Object> context = Map.of("targetUserId", targetUser.getId(), "targetUserRole", targetUser.getRole());
                assertThat(exception.getResultCode()).isEqualTo(ResultCode.INSUFFICIENT_PRIVILEGE);
                assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

        private DashboardEditUserRequest getTestCaseGoodRequest() {
            return DashboardEditUserRequest.builder()
                    .name("user123").age(12).gender("female").email("456@gmail.com").build();
        }

    }

    @Nested
    class ProfileEditUserById {

        @ParameterizedTest
        @CsvSource({"SUPER_ADMIN", "ADMIN", "USER"})
        void profileEditUserById_shouldSuccessDoNothing_whenUserExists(Role targetRole) {
            ProfileEditUserRequest request = getTestCaseGoodRequest(targetRole);
            long userId = 1L;
            when(userMapper.update(any(User.class), any())).thenReturn(1);

            userService.profileEditUserById(request, userId);

            verify(userMapper).update(any(User.class), any());
        }

        @Test
        void profileEditUserById_shouldThrowBusinessException_whenUserNotFound() {
            ProfileEditUserRequest request = getTestCaseGoodRequest(Role.SUPER_ADMIN);
            long userId = 1L;
            when(userMapper.update(any(User.class), any())).thenReturn(0);

            BusinessException exception = assertThrows(BusinessException.class, () -> userService.profileEditUserById(request, userId));
            Map<String, Object> context = Map.of("userId", userId);
            assertThat(exception.getResultCode()).isEqualTo(ResultCode.USER_UNAVAILABLE);
            assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
        }

        @Test
        void profileEditUserById_shouldThrowDataIntegrityException_whenMultipleRowsModified() {
            ProfileEditUserRequest request = getTestCaseGoodRequest(Role.ADMIN);
            long userId = 1L;
            when(userMapper.update(any(User.class), any())).thenReturn(2);

            DataIntegrityException exception = assertThrows(DataIntegrityException.class, () -> userService.profileEditUserById(request, userId));
            Map<String, Object> context = Map.of("userId", userId);
            assertThat(exception.getResultCode()).isEqualTo(ResultCode.UPDATE_AFFECTED_ROWS_INVALID);
            assertThat(exception.getContext()).usingRecursiveComparison().isEqualTo(context);
        }

        private ProfileEditUserRequest getTestCaseGoodRequest(Role role) {
            return ProfileEditUserRequest.builder()
                    .username("username789").name("user123")
                    .age(12).gender("female").email("456@gmail.com")
                    .role(role)
                    .build();
        }

    }

    @Nested
    class DeleteUserById{

        @ParameterizedTest
        @CsvSource({"SUPER_ADMIN, ADMIN", "SUPER_ADMIN, USER", "ADMIN, USER"})
        void deleteUserById_shouldDoNothing_whenCanOperate(Role currentRole, Role targetRole){
            try(MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)){
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(currentRole);
                User targetUser = getTestCaseGoodUser(targetRole);
                when(userMapper.selectOne(any())).thenReturn(targetUser);

                userService.deleteUserById(targetUser.getId());

                ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
                verify(userMapper).updateById(captor.capture());
                assertThat(captor.getValue().getId()).isEqualTo(targetUser.getId());
                assertThat(captor.getValue().getDeletedAt()).isNotNull();
            }
        }

        @Test
        void deleteUserById_shouldDoNothing_whenTargetNotFound(){
            try(MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)){
                when(userMapper.selectOne(any())).thenReturn(null);

                userService.deleteUserById(1L);

                mockedSecurityUtils.verify(SecurityUtils::getCurrentUserHighestRole, never());
            }
        }

        @ParameterizedTest
        @CsvSource({"ADMIN, ADMIN", "ADMIN, SUPER_ADMIN", "SUPER_ADMIN, SUPER_ADMIN"})
        void deleteUserById_shouldThrowAuthorizationException_whenCannotOperate(Role currentRole, Role targetRole){
            try(MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)){
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(currentRole);
                User targetUser = getTestCaseGoodUser(currentRole);
                when(userMapper.selectOne(any())).thenReturn(targetUser);

                AuthorizationException authorizationException = assertThrows(AuthorizationException.class, () -> userService.deleteUserById(targetUser.getId()));
                Map<String, Object> context = Map.of("targetUserId", targetUser.getId(), "targetUserRole", currentRole);
                assertEquals(ResultCode.INSUFFICIENT_PRIVILEGE, authorizationException.getResultCode());
                assertEquals("deleteUserById", authorizationException.getOperation());
                assertThat(authorizationException.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

        @Test
        void deleteUserById_shouldThrowDataIntegrityException_whenMultipleRowsModified(){
            try(MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)){
                mockedSecurityUtils.when(SecurityUtils::getCurrentUserHighestRole).thenReturn(Role.SUPER_ADMIN);
                User targetUser = getTestCaseGoodUser(Role.USER);
                when(userMapper.selectOne(any())).thenReturn(targetUser);
                when(userMapper.updateById(any(User.class))).thenReturn(2);

                DataIntegrityException dataIntegrityException = assertThrows(DataIntegrityException.class, () -> userService.deleteUserById(targetUser.getId()));
                Map<String, Object> context = Map.of("userId", targetUser.getId());
                assertEquals(ResultCode.DELETE_AFFECTED_ROWS_INVALID, dataIntegrityException.getResultCode());
                assertEquals("deleteUserById", dataIntegrityException.getOperation());
                assertThat(dataIntegrityException.getContext()).usingRecursiveComparison().isEqualTo(context);
            }
        }

    }

    @Nested
    class IfUserNameExists{

        @Test
        void ifUsernameExists_shouldReturnTrue_whenUsernameExists(){
            when(userMapper.existsByUsername(anyString())).thenReturn(true);
            assertTrue(userService.ifUsernameExists("username"));
        }

        @Test
        void ifUsernameExists_shouldReturnFalse_whenUsernameDoesNotExist(){
            when(userMapper.existsByUsername(anyString())).thenReturn(false);
            assertFalse(userService.ifUsernameExists("username"));
        }

    }

    @Nested
    class QueryUserInPage{

        @Captor
        ArgumentCaptor<Page<User>> captor;

        @Test
        void queryUserInPage_shouldGetCorrectResponse(){
            UserQueryRequest userQueryRequest = UserQueryRequest.builder()
                    .pageQueryRequest(new PageQueryRequest()).name("")
                    .build();
            Page<User> result = new Page<>(userQueryRequest.getPageQueryRequest().getPageNum(), userQueryRequest.getPageQueryRequest().getPageSize());
            User user1 = getTestCaseGoodUser(Role.SUPER_ADMIN);
            User user2 = User.builder()
                            .id(2L).username("username123").name("name123")
                            .password("password87654321")
                            .age(18).gender("female")
                            .email("456@qq.com").role(Role.USER)
                            .status(Status.VALID).deletedAt(null)
                            .build();
            result.setRecords(List.of(user1, user2));
            result.setTotal(result.getRecords().size());
            when(userMapper.selectPage(any(), any())).thenReturn(result);

            PageResultResponse<UserQueryResponse> response = userService.queryUserInPage(userQueryRequest);

            UserQueryResponse user1Response = UserQueryResponse.builder()
                    .id(user1.getId()).username(user1.getUsername()).name(user1.getName())
                    .age(user1.getAge()).gender(user1.getGender()).email(user1.getEmail())
                    .role(user1.getRole()).status(user1.getStatus()).build();
            UserQueryResponse user2Response = UserQueryResponse.builder()
                    .id(user2.getId()).username(user2.getUsername()).name(user2.getName())
                    .age(user2.getAge()).gender(user2.getGender()).email(user2.getEmail())
                    .role(user2.getRole()).status(user2.getStatus()).build();

            verify(userMapper).selectPage(captor.capture(), any());
            assertEquals(PageQueryRequest.DEFAULT_PAGE_NUM, captor.getValue().getCurrent());
            assertEquals(PageQueryRequest.DEFAULT_PAGE_SIZE, captor.getValue().getSize());
            assertThat(response.getRecords()).usingRecursiveComparison().isEqualTo(List.of(user1Response, user2Response));
            assertEquals(result.getRecords().size(), response.getTotal());
            assertEquals(PageQueryRequest.DEFAULT_PAGE_NUM, response.getPageNum());
            assertEquals(PageQueryRequest.DEFAULT_PAGE_SIZE, response.getPageSize());
        }

    }

    @Nested
    class FindUserByCurrentUserId{

        @Test
        void findUserByCurrentUserId_shouldGetCorrectResponse(){
            try(MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)){
                User target = getTestCaseGoodUser(Role.USER);
                mockedSecurityUtils.when(SecurityUtils::getUserIdFromContext).thenReturn(1L);
                when(userMapper.selectById(anyLong())).thenReturn(target);

                UserQueryResponse result = userService.findUserByCurrentUserId();

                assertEquals(target.getId(), result.getId());
                assertEquals(target.getUsername(), result.getUsername());
                assertEquals(target.getName(), result.getName());
                assertEquals(target.getAge(), result.getAge());
                assertEquals(target.getGender(), result.getGender());
                assertEquals(target.getEmail(), result.getEmail());
                assertEquals(target.getRole(), result.getRole());
                assertEquals(target.getStatus(), result.getStatus());
            }

        }

        @Test
        void findUserByCurrentUserId_shouldThrowUserIdNotFoundException_whenNotExistingLoggedInUser(){
            try(MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)){
                mockedSecurityUtils.when(SecurityUtils::getUserIdFromContext).thenReturn(null);

                UserIdNotFoundException userIdNotFoundException = assertThrows(UserIdNotFoundException.class, userService::findUserByCurrentUserId);

                assertEquals(ResultCode.USER_UNAVAILABLE.getMessage(), userIdNotFoundException.getMessage());
            }
        }

    }

    @Nested
    class ChangePasswordById{

        @Test
        void changePasswordById_shouldSuccess(){
            when(userMapper.updateById(any(User.class))).thenReturn(1);

            userService.changePasswordById(1L, "newPassword");

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(userMapper).updateById(captor.capture());
            assertEquals(1L, captor.getValue().getId());
            assertTrue(passwordEncoder.matches("newPassword", captor.getValue().getPassword()));
        }

        @Test
        void changePasswordById_shouldThrowDataIntegrityException_whenUpdatedMoreThanOneRows(){
            when(userMapper.updateById(any(User.class))).thenReturn(2);

            DataIntegrityException dataIntegrityException = assertThrows(DataIntegrityException.class, () ->  userService.changePasswordById(1L, "newPassword"));

            Map<String, Object> context = Map.of("userId", 1L);
            assertEquals(ResultCode.UPDATE_AFFECTED_ROWS_INVALID, dataIntegrityException.getResultCode());
            assertThat(dataIntegrityException.getContext()).usingRecursiveComparison().isEqualTo(context);
        }

        @Test
        void changePasswordById_shouldThrowBusinessException_whenUserNotFound(){
            when(userMapper.updateById(any(User.class))).thenReturn(0);

            BusinessException businessException = assertThrows(BusinessException.class, () ->  userService.changePasswordById(1L, "newPassword"));

            Map<String, Object> context = Map.of("userId", 1L);
            assertEquals(ResultCode.USER_UNAVAILABLE, businessException.getResultCode());
            assertThat(businessException.getContext()).usingRecursiveComparison().isEqualTo(context);
        }

    }

    @Nested
    class FindAuthInfoByUsername{

        @Test
        void findAuthInfoByUsername_shouldReturnCorrectData(){
            User target = getTestCaseGoodUser(Role.USER);
            when(userMapper.selectOne(any())).thenReturn(target);

            UserAuthDTO result = userService.findAuthInfoByUsername("username");

            assertEquals(target.getId(), result.getUserId());
            assertEquals(target.getUsername(), result.getUsername());
            assertEquals(target.getPassword(), result.getPasswordHash());
            assertEquals(target.getRole(), result.getRole());
            assertEquals(target.getStatus(), result.getStatus());
        }

        @Test
        void findAuthInfoByUsername_shouldReturnNull_whenUsernameNotFound(){
            when(userMapper.selectOne(any())).thenReturn(null);

            UserAuthDTO result = userService.findAuthInfoByUsername("username");

            assertNull(result);
        }

    }

    @Nested
    class FindAuthInfoByUserId{

        @Test
        void findAuthInfoByUserId_shouldReturnCorrectData(){
            User target = getTestCaseGoodUser(Role.USER);
            when(userMapper.selectOne(any())).thenReturn(target);

            UserAuthDTO result = userService.findAuthInfoByUserId(1L);

            assertEquals(target.getId(), result.getUserId());
            assertEquals(target.getUsername(), result.getUsername());
            assertEquals(target.getPassword(), result.getPasswordHash());
            assertEquals(target.getRole(), result.getRole());
            assertEquals(target.getStatus(), result.getStatus());
        }

        @Test
        void findAuthInfoByUserId_shouldReturnNull_whenUserIdNotFound(){
            when(userMapper.selectOne(any())).thenReturn(null);

            UserAuthDTO result = userService.findAuthInfoByUserId(1L);

            assertNull(result);
        }

    }

    private User getTestCaseGoodUser(Role role) {
        return User.builder()
                .id(1L).username("username").name("user")
                .password("password12345678")
                .age(1).gender("male")
                .email("123@gmail.com").role(role)
                .status(Status.VALID).deletedAt(null)
                .build();
    }

}
