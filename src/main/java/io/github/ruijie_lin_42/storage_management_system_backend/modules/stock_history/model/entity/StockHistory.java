package io.github.ruijie_lin_42.storage_management_system_backend.modules.stock_history.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * <p>
 * 
 * </p>
 *
 * @author ruijie-lin-42
 * @since 2026-06-13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("stock_history")
public class StockHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * the storage the adjustment happened (id)
     */
    private Long storageId;

    /**
     * who created this adjustment (id)
     */
    private Long createdBy;

    /**
     * when is this adjustment created
     */
    private Instant createdAt;

    /**
     * the type of transaction
     */
    private String type;

    /**
     * the number of items changed
     */
    private Integer amount;

    /**
     * reason
     */
    private String remark;
}
