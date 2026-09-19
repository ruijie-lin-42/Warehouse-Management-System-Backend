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
@Schema(description = "Response for querying items' detailed info")
public class ItemInfoDetailResponse {

    @Schema(description = "Item's id", example = "1")
    private Long id;

    @Schema(description = "Item's name", example = "ThisIsAnItemName")
    private String name;

    @Schema(description = "Item's description", example = "This is not an actual item")
    private String description;

    @Schema(description = "Item's category", example = "Electronics")
    private String category;

    @Schema(description = "Item's counting unit", example = "pieces")
    private String unit;

    @Schema(description = "Item's sku", example = "COMP-LENOVO-X1")
    private String sku;

    @Schema(description = "Item's price", example = "1234567")
    private Integer price;

}
