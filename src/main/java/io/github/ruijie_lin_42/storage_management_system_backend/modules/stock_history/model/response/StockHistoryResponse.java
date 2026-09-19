package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.response;

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
@Schema(description = "Response for a storage's stock history")
public class StockHistoryResponse {

    @Schema(description = "Stock history's id", example = "1")
    private Long id;

    @Schema(description = "Storage's id", example = "1")
    private Long storageId;

    @Schema(description = "Storage's name", example = "storage123")
    private String storageName;

    @Schema(description = "The time when the stock history is created", example = "2026-06-20T03:25:19Z")
    private String createdBy;

    @Schema(description = "The person who operated this stock change and created this stock history", example = "ADMIN")
    private Instant createdAt;

    @Schema(description = "The type of the stock adjustment", example = "ADJUSTMENT")
    private String type;

    @Schema(description = "The amount of items that have stock changed in this stock history", example = "10")
    private Integer amount;

    @Schema(description = "The stock history's remark", example = "Decrease stock for outbound")
    private String remark;

}
