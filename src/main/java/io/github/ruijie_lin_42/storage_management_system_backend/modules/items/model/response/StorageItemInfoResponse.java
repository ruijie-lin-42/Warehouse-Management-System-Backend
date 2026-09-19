package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for querying for an item's basic info in a storage")
public class StorageItemInfoResponse {

    @Schema(description = "Item's id", example = "1")
    private Long id;

    @Schema(description = "Item's name", example = "headphone123")
    private String name;

    @Schema(description = "Item's sku", example = "HEADPHONE-QC-GEN2")
    private String sku;

    @Schema(description = "Item's counting unit", example = "pieces")
    private String unit;

    @Schema(description = "Item's count in the storage", example = "100")
    private Integer count;

}
