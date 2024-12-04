package pers.jhshop.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.inventory.model.entity.Alert;
import pers.jhshop.inventory.model.req.AlertCreateReq;
import pers.jhshop.inventory.model.req.AlertQueryReq;
import pers.jhshop.inventory.model.req.AlertUpdateReq;
import pers.jhshop.inventory.model.vo.AlertVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 库存预警表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
public interface IAlertService extends IService<Alert> {

    void createBiz(AlertCreateReq createReq);

    void updateBiz(AlertUpdateReq updateReq);

    AlertVO getByIdBiz(Long id);

    Page<AlertVO> pageBiz(AlertQueryReq queryReq);

    Page<Alert> page(AlertQueryReq queryReq);

    List<Alert> listByQueryReq(AlertQueryReq queryReq);

    Map<Long, Alert> getIdEntityMap(List<Long> ids);

    Alert getOneByQueryReq(AlertQueryReq queryReq);

}
