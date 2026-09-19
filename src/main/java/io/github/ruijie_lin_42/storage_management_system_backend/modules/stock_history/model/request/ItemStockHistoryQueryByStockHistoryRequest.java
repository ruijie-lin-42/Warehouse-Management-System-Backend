package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.constants.ItemStockHistoryConstraints;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Fuzzy search for items in a storage's stock history")
public class ItemStockHistoryQueryByStockHistoryRequest {

    @NotNull
    @Schema(description = "Stock history's id", example = "1")
    private Long stockHistoryId;

    @NotNull
    @Size(max = ItemStockHistoryConstraints.ITEM_NAME_LENGTH_MAX)
    @Schema(description = "Item's name in the stock history record", example = "laptop123")
    private String itemName;

}
