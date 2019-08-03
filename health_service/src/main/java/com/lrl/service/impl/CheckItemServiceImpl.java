package com.lrl.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.lrl.dao.CheckItemDao;
import com.lrl.pojo.CheckItem;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/19 10:16
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * @param checkItem
     */
    @Transactional
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult queryPage(QueryPageBean pageBean) {
        if (!StringUtils.isEmpty(pageBean.getQueryString())) {
            pageBean.setQueryString("%"+pageBean.getQueryString()+"%");
        }
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<CheckItem> page = checkItemDao.findAllByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public int getCountFromGroupAndItemById(Integer id) {
        int count = checkItemDao.getCountFromGroupAndItemById(id);
        if (count>0) {
            throw new RuntimeException("此检查项已被依赖，无法删除");
        }
        return count;
    }

    @Override
    public void deleteItemById(Integer id) {
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findItemById(Integer id) {
        CheckItem item = checkItemDao.findItemById(id);
        return item;
    }

    /**
     * 更新表单数据
     *
     * @param checkItem
     */
    @Override
    @Transactional
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 查找所有的检查项
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}