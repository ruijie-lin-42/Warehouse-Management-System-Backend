package io.github.ruijie_lin_42.storage_management_system_backend.modules.user.model.response;

import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Role;
import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for user queries")
public class UserQueryResponse {

    @Schema(description = "User Id", example = "1")
    private Long id;

    @Schema(description = "Username, unique in system", example = "ZhangSan123")
    private String username;

    @Schema(description = "User's actual name, not required to be unique in system", example = "ZhangSan")
    private String name;

    @Schema(description = "User's age", example = "18")
    private Integer age;

    @Schema(description = "User's gender, default male/female, or user-customized gender", example = "male")
    private String gender;

    @Schema(description = "User's email address, not required to be unique in system", example = "zhangsan123@whatevermail.com")
    private String email;

    @Schema(description = "User's role", example = "USER")
    private Role role;

    @Schema(description = "User's status, represents whether the user obtained is valid or banned", example = "VALID")
    private Status status;
}
