package com.lrl.service;

import com.lrl.pojo.CheckItem;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/19 10:15
 */
public interface CheckItemService {
    /**
     * add item
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * query page
     * @param pageBean
     * @return
     */
    PageResult queryPage(QueryPageBean pageBean);

    /**
     * get cnt
     * @param id
     * @return
     */
    int getCountFromGroupAndItemById(Integer id);

    /**
     * 根据id删除item
     * @param id
     */
    void deleteItemById(Integer id);

    /**
     * 根据id产找item
     * @param id
     * @return
     */
    CheckItem findItemById(Integer id);

    /**
     * 更新表单数据
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 查找所有的检查项
     * @return
     */
    List<CheckItem> findAll();

}