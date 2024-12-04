package pers.jhshop.inventory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.inventory.consts.JhShopInventoryApiConstants;
import pers.jhshop.inventory.model.req.InventoryCreateReq;
import pers.jhshop.inventory.model.req.InventoryQueryReq;
import pers.jhshop.inventory.model.req.InventoryUpdateReq;
import pers.jhshop.inventory.model.vo.InventoryVO;
import pers.jhshop.inventory.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 库存表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Slf4j
@RestController
@RequestMapping(JhShopInventoryApiConstants.API_USER + "inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final IInventoryService inventoryService;

    @PostMapping("create")
    public ResultBo create(@RequestBody InventoryCreateReq createReq) {
        inventoryService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody InventoryUpdateReq updateReq) {
        inventoryService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<InventoryVO> getById(Long id) {
        InventoryVO vo = inventoryService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<InventoryVO>> page(@RequestBody InventoryQueryReq queryReq) {
        Page page = inventoryService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

