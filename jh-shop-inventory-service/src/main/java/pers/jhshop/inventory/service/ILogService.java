package pers.jhshop.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.inventory.model.entity.Log;
import pers.jhshop.inventory.model.req.LogCreateReq;
import pers.jhshop.inventory.model.req.LogQueryReq;
import pers.jhshop.inventory.model.req.LogUpdateReq;
import pers.jhshop.inventory.model.vo.LogVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 库存变动记录表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
public interface ILogService extends IService<Log> {

    void createBiz(LogCreateReq createReq);

    void updateBiz(LogUpdateReq updateReq);

    LogVO getByIdBiz(Long id);

    Page<LogVO> pageBiz(LogQueryReq queryReq);

    Page<Log> page(LogQueryReq queryReq);

    List<Log> listByQueryReq(LogQueryReq queryReq);

    Map<Long, Log> getIdEntityMap(List<Long> ids);

    Log getOneByQueryReq(LogQueryReq queryReq);

}
