package pers.jhshop.inventory.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import pers.jhshop.common.entity.BaseVo;

/**
 * <p>
 * 库存表VO
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "InventoryVO", description = "库存表列表展示VO")
public class InventoryVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品ID，外键关联商品表")
    private Long productId;

    @ApiModelProperty(value = "商品库存数量")
    private Integer stock;

    @ApiModelProperty(value = "安全库存数量")
    private Integer safeStock;

    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "描述")
    private String description;

}
