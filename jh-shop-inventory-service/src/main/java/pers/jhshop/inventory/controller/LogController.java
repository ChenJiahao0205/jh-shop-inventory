package pers.jhshop.inventory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.inventory.consts.JhShopInventoryApiConstants;
import pers.jhshop.inventory.model.req.LogCreateReq;
import pers.jhshop.inventory.model.req.LogQueryReq;
import pers.jhshop.inventory.model.req.LogUpdateReq;
import pers.jhshop.inventory.model.vo.LogVO;
import pers.jhshop.inventory.service.ILogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 库存变动记录表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Slf4j
@RestController
@RequestMapping(JhShopInventoryApiConstants.API_USER + "log")
@RequiredArgsConstructor
public class LogController {
    private final ILogService logService;

    @PostMapping("create")
    public ResultBo create(@RequestBody LogCreateReq createReq) {
        logService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody LogUpdateReq updateReq) {
        logService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<LogVO> getById(Long id) {
        LogVO vo = logService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<LogVO>> page(@RequestBody LogQueryReq queryReq) {
        Page page = logService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

