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
@Schema(description = "Response for category fuzzy search query")
public class CategoryResponse {

    @Schema(description = "Category's id", example = "1")
    private Long id;

    @Schema(description = "Category's name", example = "Electronics")
    private String name;

}
