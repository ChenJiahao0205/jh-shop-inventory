package pers.jhshop.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.inventory.model.req.LogCreateReq;
import pers.jhshop.inventory.model.req.LogQueryReq;
import pers.jhshop.inventory.model.req.LogUpdateReq;
import pers.jhshop.inventory.model.vo.LogVO;
import pers.jhshop.inventory.model.entity.Log;
import pers.jhshop.inventory.mapper.LogMapper;
import pers.jhshop.inventory.service.ILogService;
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
 * 库存变动记录表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(LogCreateReq createReq) {


        Log entity = new Log();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(LogUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Log entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("库存变动记录表不存在");
        }

        // 2.重复性判断
        Log entityToUpdate = new Log();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public LogVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Log entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("库存变动记录表不存在");
        }

        LogVO vo = new LogVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<LogVO> pageBiz(LogQueryReq queryReq) {
        Page<Log> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Log> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<LogVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Log> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<LogVO> vos = records.stream().map(record -> {
            LogVO vo = new LogVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Log> page(LogQueryReq queryReq) {
        Page<Log> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Log> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Log> listByQueryReq(LogQueryReq queryReq) {
        LambdaQueryWrapper<Log> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Log> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Log> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Log> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Log::getId, ids);
        List<Log> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Log::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Log getOneByQueryReq(LogQueryReq queryReq) {
        LambdaQueryWrapper<Log> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Log> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Log> getLambdaQueryWrapper(LogQueryReq queryReq) {
        LambdaQueryWrapper<Log> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Log::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getProductId()), Log::getProductId, queryReq.getProductId());
        queryWrapper.eq(Objects.nonNull(queryReq.getChangeAmount()), Log::getChangeAmount, queryReq.getChangeAmount());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getReason()), Log::getReason, queryReq.getReason());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getReasonLike()), Log::getReason, queryReq.getReasonLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Log::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getDescription()), Log::getDescription, queryReq.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getDescriptionLike()), Log::getDescription, queryReq.getDescriptionLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Log::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
