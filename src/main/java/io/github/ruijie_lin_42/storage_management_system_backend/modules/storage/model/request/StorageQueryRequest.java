package io.github.ruijie_lin_42.storage_management_system_backend.modules.storage.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.common.request.PageQueryRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.storage.constants.StorageConstraints;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Query storage by name with pagination request, can be applied to fuzzy search or precise search")
public class StorageQueryRequest {

    @NotNull
    @Valid
    private PageQueryRequest pageQueryRequest;

    @NotNull
    @Size(max = StorageConstraints.NAME_LENGTH_MAX)
    @Schema(description = "Storage's name", example = "ThisIsAStupidStorageName")
    private String name;

}
