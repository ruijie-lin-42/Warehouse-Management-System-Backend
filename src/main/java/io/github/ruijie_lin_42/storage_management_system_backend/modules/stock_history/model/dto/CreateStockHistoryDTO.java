package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.dto;

import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.StockAdjustmentType;
import io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.constants.StockHistoryConstraints;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStockHistoryDTO {

    @NotNull
    private Long storageId;

    @NotNull
    private Long createdBy;

    @NotNull
    private StockAdjustmentType type;

    @NotNull
    @Min(StockHistoryConstraints.AMOUNT_MIN)
    @Max(StockHistoryConstraints.AMOUNT_MAX)
    private Integer amount;

    @NotNull
    @NotBlank
    @Min(StockHistoryConstraints.REMARK_LENGTH_MIN)
    @Max(StockHistoryConstraints.REMARK_LENGTH_MAX)
    private String remark;


}
