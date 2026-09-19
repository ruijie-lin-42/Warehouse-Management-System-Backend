package io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for login requests")
public class LoginResponse {

    @Schema(description = "The access token that is being given to the user", example = "jwt.userinfo.sign")
    private String accessToken;

}
