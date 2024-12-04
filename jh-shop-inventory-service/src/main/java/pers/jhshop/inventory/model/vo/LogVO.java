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
 * 库存变动记录表VO
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LogVO", description = "库存变动记录表列表展示VO")
public class LogVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存变动记录ID，主键")
    private Long id;

    @ApiModelProperty(value = "商品ID，外键关联商品表")
    private Integer productId;

    @ApiModelProperty(value = "库存变动数量")
    private Integer changeAmount;

    @ApiModelProperty(value = "变动原因")
    private String reason;

    @ApiModelProperty(value = "变动时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "描述")
    private String description;

}
