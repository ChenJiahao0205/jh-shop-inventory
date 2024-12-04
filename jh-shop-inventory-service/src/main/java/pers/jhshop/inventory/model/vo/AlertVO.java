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
 * 库存预警表VO
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AlertVO", description = "库存预警表列表展示VO")
public class AlertVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品ID，外键关联商品表")
    private Long productId;

    @ApiModelProperty(value = "当前库存量")
    private Integer stock;

    @ApiModelProperty(value = "安全库存量")
    private Integer safeStock;

    @ApiModelProperty(value = "预警时间")
    private LocalDateTime alertTime;

    @ApiModelProperty(value = "描述")
    private String description;

}
