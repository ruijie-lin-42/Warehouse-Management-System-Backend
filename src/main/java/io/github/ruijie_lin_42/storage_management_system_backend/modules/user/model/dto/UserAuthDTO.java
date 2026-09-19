package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.dto;

import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Role;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthDTO {

    @NotNull
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    @NotBlank
    private String passwordHash;

    @NotNull
    private Role role;

    @NotNull
    private Status status;

}
