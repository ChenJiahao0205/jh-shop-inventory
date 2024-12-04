package pers.jhshop.inventory.model.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.inventory.model.entity.Log;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 库存变动记录表查询Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LogQueryReq", description = "库存变动记录表查询Req")
public class LogQueryReq extends Page<Log> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存变动记录ID，主键")
    private Long id;

    @ApiModelProperty(value = "商品ID，外键关联商品表")
    private Integer productId;

    @ApiModelProperty(value = "库存变动数量")
    private Integer changeAmount;

    @ApiModelProperty(value = "变动原因")
    private String reason;

    @ApiModelProperty(value = "变动原因-模糊匹配")
    private String reasonLike;

    @ApiModelProperty(value = "变动时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "描述-模糊匹配")
    private String descriptionLike;

    @ApiModelProperty(value = "生效标志(TRUE-生效, FALSE-失效)")
    private Boolean validFlag;



}
