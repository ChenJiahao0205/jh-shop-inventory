package pers.jhshop.inventory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.inventory.consts.JhShopInventoryApiConstants;
import pers.jhshop.inventory.model.req.AlertCreateReq;
import pers.jhshop.inventory.model.req.AlertQueryReq;
import pers.jhshop.inventory.model.req.AlertUpdateReq;
import pers.jhshop.inventory.model.vo.AlertVO;
import pers.jhshop.inventory.service.IAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 库存预警表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Slf4j
@RestController
@RequestMapping(JhShopInventoryApiConstants.API_USER + "alert")
@RequiredArgsConstructor
public class AlertController {
    private final IAlertService alertService;

    @PostMapping("create")
    public ResultBo create(@RequestBody AlertCreateReq createReq) {
        alertService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody AlertUpdateReq updateReq) {
        alertService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<AlertVO> getById(Long id) {
        AlertVO vo = alertService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<AlertVO>> page(@RequestBody AlertQueryReq queryReq) {
        Page page = alertService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

