package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.request;

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
@Schema(description = "Edit user request through dashboard")
public class DashboardEditUserRequest {

    @NotNull
    @NotBlank
    @Size(min = UserConstraints.NAME_LENGTH_MIN, max = UserConstraints.NAME_LENGTH_MAX)
    @Schema(description = "User's name", example = "ZhangSan")
    private String name;

    @NotNull
    @Min(UserConstraints.AGE_MIN)
    @Max(UserConstraints.AGE_MAX)
    @Schema(description = "User's age", example = "18")
    private Integer age;

    @NotNull
    @NotBlank
    @Size(min = UserConstraints.GENDER_LENGTH_MIN, max = UserConstraints.GENDER_LENGTH_MAX)
    @Schema(description = "User's gender", example = "male")
    private String gender;

    @NotNull
    @NotBlank
    @Email
    @Size(max = UserConstraints.EMAIL_LENGTH_MAX)
    @Schema(description = "User's email address, not required to be unique in system, has to follow pattern xxx@xxx.com", example = "zhangsan123@whatevermail.com")
    private String email;

}
