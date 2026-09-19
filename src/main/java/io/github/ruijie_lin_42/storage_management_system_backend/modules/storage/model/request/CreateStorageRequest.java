package io.github.ruijie_lin_42.storage_management_system_backend.modules.storage.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.storage.constants.StorageConstraints;
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
@Schema(description = "Create storage request")
public class CreateStorageRequest {

    @NotNull
    @NotBlank
    @Size(min = StorageConstraints.NAME_LENGTH_MIN, max = StorageConstraints.NAME_LENGTH_MAX)
    @Schema(description = "Storage's name, has to be unique in system", example = "MyStorage")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = StorageConstraints.ADDRESS_LENGTH_MIN, max = StorageConstraints.ADDRESS_LENGTH_MAX)
    @Schema(description = "Storage's address", example = "Some Rd #114514")
    private String address;

    @NotNull
    @Schema(description = "Storage manager's id", example = "1")
    private Long managerId;

    @NotNull
    @Schema(description = "The user's id who created the storage", example = "1")
    private Long createdBy;

    @Size(max = StorageConstraints.REMARK_LENGTH_MAX)
    @Schema(description = "The remark on the storage", example = "Jerry's secret storage, used to keep cheese and hid from Tom :)")
    private String remark;

}
