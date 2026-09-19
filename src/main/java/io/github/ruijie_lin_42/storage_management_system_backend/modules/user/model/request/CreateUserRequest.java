package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Role;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.user.constants.UserConstraints;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Create user request")
public class CreateUserRequest {

    @NotNull
    @NotBlank
    @Size(min = UserConstraints.USERNAME_LENGTH_MIN, max = UserConstraints.USERNAME_LENGTH_MAX)
    @Schema(description = "Username, has to be unique in system", example = "ZhangSan123")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = UserConstraints.NAME_LENGTH_MIN, max = UserConstraints.NAME_LENGTH_MAX)
    @Schema(description = "User's actual name, not required to be unique", example = "ZhangSan")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = UserConstraints.PASSWORD_LENGTH_MIN, max = UserConstraints.PASSWORD_LENGTH_MAX)
    @Schema(description = "Password", example = "ThisIsAWeakPassword")
    private String password;

    @NotNull
    @Min(UserConstraints.AGE_MIN)
    @Max(UserConstraints.AGE_MAX)
    @Schema(description = "User's age", example = "18")
    private Integer age;

    @NotNull
    @NotBlank
    @Size(min = UserConstraints.GENDER_LENGTH_MIN, max = UserConstraints.GENDER_LENGTH_MAX)
    @Schema(description = "User's gender, default male/female, can also be customized gender", example = "male")
    private String gender;

    @NotNull
    @NotBlank
    @Email
    @Size(max = UserConstraints.EMAIL_LENGTH_MAX)
    @Schema(description = "User's email address, not required to be unique in system, has to follow the pattern 'xxx@xxx.com'", example = "zhangsan123@whatevermail.com")
    private String email;

    @NotNull
    @Schema(description = "User's role", example = "USER")
    private Role role;

}
