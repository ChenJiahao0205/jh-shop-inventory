package pers.jhshop.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.inventory.model.entity.Inventory;
import pers.jhshop.inventory.model.req.InventoryCreateReq;
import pers.jhshop.inventory.model.req.InventoryQueryReq;
import pers.jhshop.inventory.model.req.InventoryUpdateReq;
import pers.jhshop.inventory.model.vo.InventoryVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 库存表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
public interface IInventoryService extends IService<Inventory> {

    void createBiz(InventoryCreateReq createReq);

    void updateBiz(InventoryUpdateReq updateReq);

    InventoryVO getByIdBiz(Long id);

    Page<InventoryVO> pageBiz(InventoryQueryReq queryReq);

    Page<Inventory> page(InventoryQueryReq queryReq);

    List<Inventory> listByQueryReq(InventoryQueryReq queryReq);

    Map<Long, Inventory> getIdEntityMap(List<Long> ids);

    Inventory getOneByQueryReq(InventoryQueryReq queryReq);

}
