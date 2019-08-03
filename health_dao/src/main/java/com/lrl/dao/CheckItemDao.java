package com.lrl.dao;

import com.github.pagehelper.Page;
import com.lrl.pojo.CheckItem;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/19 10:26
 */
public interface CheckItemDao {
    /**
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * @param queryString
     * @return
     */
    Page<CheckItem> findAllByCondition(String queryString);

    /**
     * 获得GroupAndItem表中对应的id的计数值
     * @return
     * @param id
     */
    int getCountFromGroupAndItemById(Integer id);

    /**
     * 通过id删除对应的item
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查找item
     * @param id
     * @return
     */
    CheckItem findItemById(Integer id);

    /**
     * 更新数据
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 查找所有检查项
     * @return
     */
    List<CheckItem> findAll();

}