package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper interface
 * </p>
 *
 * @author ruijie-lin-42
 * @since 2026-05-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    boolean existsByUsername(@Param("username") String username);

}
