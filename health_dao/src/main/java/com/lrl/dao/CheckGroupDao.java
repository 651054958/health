package com.lrl.dao;

import com.github.pagehelper.Page;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    /**
     * find checkgroup pages by condition string
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * get count by id from tab item_group
     * @param id
     * @return
     */
    int getCountById(Integer id);

    /**
     * delete group table by id
     * @param id
     */
    void deleteById(Integer id);

    /**
     * find all checkitems
     * @return
     */
    List<CheckItem> findAll();

    /**
     * save group
     * @param checkGroup
     */
    void saveGroup(CheckGroup checkGroup);

    /**
     * set groupAndItem table
     * @param map
     */
    void addGroupAndItem(Map map);

    /**
     * 通过id查找对应的checkgroup
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过id查找对应的itemIds
     *
     * @param id
     * @return
     */
    Integer[] findItemsById(int id);

    /**
     * 更新group数据
     * @param checkGroup
     */
    void updateCheckGroup(CheckGroup checkGroup);

    /**
     * 删除关系表中的对应id的数据
     * @param checkGroupId
     */
    void deleteRelationTabById(Integer checkGroupId);

    /**
     * 设置更新关系表中的数据
     * @param map
     */
    void setRelationForGroupAndItem(Map map);

    /**
     * find all groups
     * @return
     */
    List<CheckGroup> findAllGroups();

}
