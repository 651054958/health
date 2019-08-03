package com.lrl.service;

import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    /**
     * checkGroup find pageresult
     * @param pageBean
     * @return
     */
    PageResult findPage(QueryPageBean pageBean);

    /**
     * check count
     * @param id
     */
    void checkCountFromItemAndGroupById(Integer id);

    /**
     * delete group by id
     * @param id
     */
    void deleteById(Integer id);

    /**
     * add group
     * @param checkGroup
     * @param checkitemIds
     */
    void addGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 通过id查找对应的checkgroup
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过id查找对应的itemIds
     * @param id
     * @return
     */
    Integer[] findItemsById(int id);

    /**
     * 更新checkgoup表 和 关系表 数据
     * @param checkGroup
     * @param checkitemids
     */
    void update(CheckGroup checkGroup, Integer[] checkitemids);

    /**
     * findAll Groups
     * @return
     */
    List<CheckGroup> findAllGroup();

}
