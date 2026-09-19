package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for querying for category as a tree")
public class CategoryTreeResponse {

    @Schema(description = "Current category(tree node)'s id", example = "1")
    private Long id;

    @Schema(description = "Current category(tree node)'s name", example = "Electronics")
    private String name;

    @Schema(description = "Current category's sub category (current tree node's child nodes)")
    private List<CategoryTreeResponse> children;

}
