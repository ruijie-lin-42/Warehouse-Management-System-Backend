package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.items.constants.StockChangeConstraints;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Stock change request for one single item in a storage")
public class StockChangeRequest {

    @NotNull
    @Schema(description = "Item's id, which stock is being changed", example = "1")
    private Long itemId;

    @NotNull
    @Min(StockChangeConstraints.AMOUNT_CHANGE_MIN)
    @Max(StockChangeConstraints.AMOUNT_CHANGE_MAX)
    @Schema(description = "The amount of change, positive -> increase, negative -> decrease", example = "100")
    private Integer change;

}
