package pers.jhshop.inventory.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 库存预警表修改Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AlertUpdateReq", description = "库存预警表修改Req")
public class AlertUpdateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空")
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

    @ApiModelProperty(value = "生效标志(TRUE-生效, FALSE-失效)")
    private Boolean validFlag;
}
