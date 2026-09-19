package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.dto;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.constants.ItemStockHistoryConstraints;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateItemStockHistoryDTO {

    @NotNull
    private Long stockHistoryId;

    @NotNull
    private Long itemId;

    @NotNull
    @Min(ItemStockHistoryConstraints.STOCK_BEFORE_MIN)
    @Max(ItemStockHistoryConstraints.STOCK_BEFORE_MAX)
    private Integer stockBefore;

    @Min(ItemStockHistoryConstraints.AMOUNT_CHANGE_MIN)
    @Max(ItemStockHistoryConstraints.AMOUNT_CHANGE_MAX)
    private Integer amountChange;

    @Min(ItemStockHistoryConstraints.STOCK_AFTER_MIN)
    @Max(ItemStockHistoryConstraints.STOCK_AFTER_MAX)
    private Integer stockAfter;

}
