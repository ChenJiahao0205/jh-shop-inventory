package pers.jhshop.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.inventory.model.req.InventoryCreateReq;
import pers.jhshop.inventory.model.req.InventoryQueryReq;
import pers.jhshop.inventory.model.req.InventoryUpdateReq;
import pers.jhshop.inventory.model.vo.InventoryVO;
import pers.jhshop.inventory.model.entity.Inventory;
import pers.jhshop.inventory.mapper.InventoryMapper;
import pers.jhshop.inventory.service.IInventoryService;
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
 * 库存表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(InventoryCreateReq createReq) {
        if (Objects.isNull(createReq.getProductId())) {
            throw new ServiceException("商品ID，外键关联商品表不能为空");
        }



        Inventory entity = new Inventory();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(InventoryUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Inventory entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("库存表不存在");
        }

        // 2.重复性判断
        Inventory entityToUpdate = new Inventory();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public InventoryVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Inventory entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("库存表不存在");
        }

        InventoryVO vo = new InventoryVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<InventoryVO> pageBiz(InventoryQueryReq queryReq) {
        Page<Inventory> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Inventory> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<InventoryVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Inventory> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<InventoryVO> vos = records.stream().map(record -> {
            InventoryVO vo = new InventoryVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Inventory> page(InventoryQueryReq queryReq) {
        Page<Inventory> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Inventory> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Inventory> listByQueryReq(InventoryQueryReq queryReq) {
        LambdaQueryWrapper<Inventory> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Inventory> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Inventory> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Inventory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Inventory::getId, ids);
        List<Inventory> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Inventory::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Inventory getOneByQueryReq(InventoryQueryReq queryReq) {
        LambdaQueryWrapper<Inventory> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Inventory> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Inventory> getLambdaQueryWrapper(InventoryQueryReq queryReq) {
        LambdaQueryWrapper<Inventory> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Inventory::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getProductId()), Inventory::getProductId, queryReq.getProductId());
        queryWrapper.eq(Objects.nonNull(queryReq.getStock()), Inventory::getStock, queryReq.getStock());
        queryWrapper.eq(Objects.nonNull(queryReq.getSafeStock()), Inventory::getSafeStock, queryReq.getSafeStock());
        queryWrapper.eq(Objects.nonNull(queryReq.getWarehouseId()), Inventory::getWarehouseId, queryReq.getWarehouseId());
        queryWrapper.eq(Objects.nonNull(queryReq.getUpdatedAt()), Inventory::getUpdatedAt, queryReq.getUpdatedAt());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getDescription()), Inventory::getDescription, queryReq.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getDescriptionLike()), Inventory::getDescription, queryReq.getDescriptionLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Inventory::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
