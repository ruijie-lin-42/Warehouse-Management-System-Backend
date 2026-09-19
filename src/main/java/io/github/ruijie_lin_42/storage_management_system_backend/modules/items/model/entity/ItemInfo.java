package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ruijie-lin-42
 * @since 2026-06-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("item_info")
public class ItemInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * item name
     */
    private String name;

    /**
     * item description
     */
    private String description;

    /**
     * category
     */
    private Long categoryId;

    /**
     * unit for counting
     */
    private String unit;

    /**
     * stock keeping unit
     */
    private String sku;

    /**
     * price for a single item, in US dollar
     */
    private Integer price;
}
