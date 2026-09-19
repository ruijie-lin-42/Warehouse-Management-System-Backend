package io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.constants.AuthConstraints;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Login request")
public class LoginRequest {

    @NotNull
    @NotBlank
    @Size(min = AuthConstraints.USERNAME_LENGTH_MIN, max = AuthConstraints.USERNAME_LENGTH_MAX)
    @Schema(description = "Username", example = "ZhangSan123")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = AuthConstraints.PASSWORD_LENGTH_MIN, max = AuthConstraints.PASSWORD_LENGTH_MAX)
    @Schema(description = "Password", example = "12345678")
    private String password;

}
