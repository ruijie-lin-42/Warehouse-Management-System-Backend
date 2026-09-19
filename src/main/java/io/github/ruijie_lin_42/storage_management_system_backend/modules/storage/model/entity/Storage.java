package io.github.ruijie_lin_42.storage_management_system_backend.modules.storage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2026-06-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Storage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * storage name
     */
    private String name;

    /**
     * location
     */
    private String address;

    /**
     * person in charge, should not be user-level
     */
    private Long managerId;

    /**
     * when the storage is created
     */
    private Instant createdAt;

    /**
     * person who created the storage
     */
    private Long createdBy;

    /**
     * the last update time of the storage
     */
    private Instant updatedAt;

    /**
     * the last person updated the storage
     */
    private Long updatedBy;

    /**
     * remark
     */
    private String remark;

    /**
     * when the storage is deleted, null if not deleted
     */
    private Instant deletedAt;
}
