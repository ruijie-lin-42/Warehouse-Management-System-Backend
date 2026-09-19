package io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.model.dto;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.model.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    private LoginRequest loginRequest;
    private String ip;
    private String userAgent;

}
