package io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String tokenHash;
    private Instant expiration;
    private Instant revokedAt;

}
