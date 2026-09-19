package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.common.request.PageQueryRequest;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.items.constants.ItemInfoConstraints;
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
@Schema(description = "Query for items in a storage")
public class ItemsQueryRequest {

    @NotNull
    @Valid
    private PageQueryRequest pageQueryRequest;

    @NotNull
    @Schema(description = "Storage's id", example = "1")
    private Long storageId;

    @NotNull
    @Size(max = ItemInfoConstraints.NAME_LENGTH_MAX)
    @Schema(description = "Item's name, fuzzy search keyword", example = "itemName")
    private String name;

    @NotNull
    @Size(max = ItemInfoConstraints.NAME_LENGTH_MAX)
    @Schema(description = "Item's category, fuzzy search keyword", example = "thisIsACategory")
    private String category;

}
