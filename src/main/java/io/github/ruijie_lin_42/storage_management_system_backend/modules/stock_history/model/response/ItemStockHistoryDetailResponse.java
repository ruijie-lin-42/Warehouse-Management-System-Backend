package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.response;

import io.github.ruijie_lin_42.storage_management_system_backend.common.enums.StockAdjustmentType;
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
@Schema(description = "Response for querying for all stock histories for an exact item")
public class ItemStockHistoryDetailResponse {

    @Schema(description = "Item's stock history's id", example = "1")
    private Long id;

    @Schema(description = "Stock history's id", example = "1")
    private Long stockHistoryId;

    @Schema(description = "Stock count before change", example = "100")
    private Integer stockBefore;

    @Schema(description = "The amount of stocks changed", example = "100")
    private Integer amountChange;

    @Schema(description = "Stock count after change", example = "200")
    private Integer stockAfter;

    @Schema(description = "Storage's name", example = "storage123")
    private String storageName;

    @Schema(description = "The time when this stock history is created", example = "2026-06-20T03:25:19Z")
    private Instant createdAt;

    @Schema(description = "The user's name, who operated this stock change", example = "ADMIN")
    private String operatorName;

    @Schema(description = "The stock adjustment's type", example = "ADJUSTMENT")
    private StockAdjustmentType type;

    @Schema(description = "The amount of items adjusted in the same stock history", example = "7")
    private Integer amount;

    @Schema(description = "The stock change's remark", example = "Adjust for inbound")
    private String remark;

}
