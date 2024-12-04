package pers.jhshop.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.inventory.model.req.AlertCreateReq;
import pers.jhshop.inventory.model.req.AlertQueryReq;
import pers.jhshop.inventory.model.req.AlertUpdateReq;
import pers.jhshop.inventory.model.vo.AlertVO;
import pers.jhshop.inventory.model.entity.Alert;
import pers.jhshop.inventory.mapper.AlertMapper;
import pers.jhshop.inventory.service.IAlertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.jhshop.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * <p>
 * 库存预警表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlertServiceImpl extends ServiceImpl<AlertMapper, Alert> implements IAlertService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(AlertCreateReq createReq) {
        if (Objects.isNull(createReq.getProductId())) {
            throw new ServiceException("商品ID，外键关联商品表不能为空");
        }



        Alert entity = new Alert();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(AlertUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Alert entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("库存预警表不存在");
        }

        // 2.重复性判断
        Alert entityToUpdate = new Alert();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public AlertVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Alert entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("库存预警表不存在");
        }

        AlertVO vo = new AlertVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<AlertVO> pageBiz(AlertQueryReq queryReq) {
        Page<Alert> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Alert> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<AlertVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Alert> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<AlertVO> vos = records.stream().map(record -> {
            AlertVO vo = new AlertVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Alert> page(AlertQueryReq queryReq) {
        Page<Alert> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Alert> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Alert> listByQueryReq(AlertQueryReq queryReq) {
        LambdaQueryWrapper<Alert> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Alert> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Alert> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Alert> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Alert::getId, ids);
        List<Alert> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Alert::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Alert getOneByQueryReq(AlertQueryReq queryReq) {
        LambdaQueryWrapper<Alert> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Alert> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Alert> getLambdaQueryWrapper(AlertQueryReq queryReq) {
        LambdaQueryWrapper<Alert> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Alert::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getProductId()), Alert::getProductId, queryReq.getProductId());
        queryWrapper.eq(Objects.nonNull(queryReq.getStock()), Alert::getStock, queryReq.getStock());
        queryWrapper.eq(Objects.nonNull(queryReq.getSafeStock()), Alert::getSafeStock, queryReq.getSafeStock());
        queryWrapper.eq(Objects.nonNull(queryReq.getAlertTime()), Alert::getAlertTime, queryReq.getAlertTime());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getDescription()), Alert::getDescription, queryReq.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getDescriptionLike()), Alert::getDescription, queryReq.getDescriptionLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Alert::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
