package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Role;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Status;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * <p>
 *  user entity
 * </p>
 *
 * @author ruijie-lin-42
 * @since 2026-05-24
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * full name
     */
    private String name;

    /**
     * password
     */
    private String password;

    /**
     * age
     */
    private Integer age;

    /**
     * gender: male, female, others
     */
    private String gender;

    /**
     * e-mail
     */
    private String email;

    /**
     * role: 0=SUPER_ADMIN，1=ADMIN，2=USER
     */
    private Role role;

    /**
     * user status: 0=valid, 1=banned
     */
    private Status status;

    /**
     * when the user is deleted, null if not deleted
     */
    private Instant deletedAt;
}
