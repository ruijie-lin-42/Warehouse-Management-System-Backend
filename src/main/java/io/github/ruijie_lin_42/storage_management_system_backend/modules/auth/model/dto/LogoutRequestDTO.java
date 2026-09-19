package io.github.ruijie_lin_42.storage_management_system_backend.modules.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequestDTO {

    private String tokenValue;
    private String ip;
    private String userAgent;

}
