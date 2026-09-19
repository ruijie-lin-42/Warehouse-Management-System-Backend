package io.github.ruijie_lin_42.storage_management_system_backend.modules.items.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Items implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * item's id
     */
    private Long itemId;

    /**
     * storage id
     */
    private Long storageId;

    /**
     * items count
     */
    private Integer count;

    
}
