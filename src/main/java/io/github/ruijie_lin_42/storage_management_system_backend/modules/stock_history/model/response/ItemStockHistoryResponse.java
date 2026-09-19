package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for a simple item's stock history")
public class ItemStockHistoryResponse {

    @Schema(description = "Item's stock history's id", example = "1")
    private Long id;

    @Schema(description = "Item's id", example = "1")
    private Long itemId;

    @Schema(description = "Item's name", example = "water")
    private String itemName;

    @Schema(description = "The stock count before the stock change", example = "123")
    private Integer stockBefore;

    @Schema(description = "The amount of stocks changed", example = "-123")
    private Integer amountChange;

    @Schema(description = "The stock count after the stock change", example = "0")
    private Integer stockAfter;

}
