package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.request;

import io.github.ruijie_lin_42.storage_management_system_backend.modules.items.constants.StockChangeConstraints;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Change items' stock counts in a storage request")
public class ItemsStockChangeRequest {

    @NotNull
    @Schema(description = "storage's id, which items' stock in it is being changed", example = "1")
    private Long storageId;

    @NotNull
    @Size(min=StockChangeConstraints.STOCK_CHANGE_LIST_MIN)
    @Valid
    private List<@NotNull StockChangeRequest> changedStocks;

    @NotNull
    @NotBlank
    @Size(min = StockChangeConstraints.REMARK_LENGTH_MIN, max = StockChangeConstraints.REMARK_LENGTH_MAX)
    @Schema(description = "This stock change operation's remark", example = "3 headphones are sold")
    private String remark;

}
