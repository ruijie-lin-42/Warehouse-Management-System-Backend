package io.github.ruijie_lin_42.storage_management_system_backend.modules.storage.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for storage queries")
public class StorageResponse {

    @Schema(description = "Storage's id", example = "1")
    private Long id;

    @Schema(description = "Storage's name, unique in system", example = "ThisIsASmartassStorageName")
    private String name;

    @Schema(description = "Storage's address", example = "NotAnActual Rd #42")
    private String address;

    @Schema(description = "Storage's manager's id", example = "1")
    private Long managerId;

    @Schema(description = "Storage's manager's name", example = "WoShiNiDie")
    private String managerName;

    @Schema(description = "The time the storage is created, uses UTC time", example = "2026-06-12T04:42:18Z")
    private Instant createdAt;

    @Schema(description = "The user's name, who created the storage", example = "Sponge Bob")
    private String createdBy;

    @Schema(description = "The last time the storage's info is edited, uses UTC time", example = "2026-06-25T05:40:40Z")
    private Instant updatedAt;

    @Schema(description = "The user's name, who last edited the storage's info", example = "Patrick")
    private String updatedBy;

    @Schema(description = "The storage's remark", example = "I know this is a really bad remark on a storage, but I really don't know what to write here so")
    private String remark;

}
