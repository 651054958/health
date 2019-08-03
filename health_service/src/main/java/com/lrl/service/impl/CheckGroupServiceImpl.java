package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.CheckGroupDao;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.CheckItem;
import com.lrl.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/20 21:06
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao groupDao;

    /**
     * checkGroup find pageresult
     *
     * @param pageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        if (!StringUtils.isEmpty(pageBean.getQueryString())) {
            pageBean.setQueryString("%"+pageBean.getQueryString()+"%");
        }
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<CheckGroup> page = groupDao.findByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * check count
     *
     * @param id
     */
    @Override
    public void checkCountFromItemAndGroupById(Integer id) {
        int count = groupDao.getCountById(id);
        if (count>0) {
            //this group has been depended
            throw new RuntimeException(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * delete group by id
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        groupDao.deleteById(id);
    }


    /**
     * add group
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Transactional
    @Override
    public void addGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.add group
        groupDao.saveGroup(checkGroup);

        //2.add itemAndGroup info
        setGroupAndItem(checkGroup,checkitemIds);
    }

    /**
     * 通过id查找对应的checkgroup
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return groupDao.findById(id);
    }

    /**
     * 通过id查找对应的itemIds
     *
     * @param id
     * @return
     */
    @Override
    public Integer[] findItemsById(int id) {
        return groupDao.findItemsById(id);
    }

    /**
     * 更新checkgoup表 和 关系表 数据
     *
     * @param checkGroup
     * @param checkitemids
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemids) {
        Integer checkGroupId = checkGroup.getId();
        Map<String,Integer> map = new HashMap<>();
        //删除关系数据
        groupDao.deleteRelationTabById(checkGroupId);
        //set relations
        if (checkitemids.length>0 && checkitemids!=null) {
            for (Integer checkitemid : checkitemids) {
                map.put("checkGroupId",checkGroupId);
                map.put("checkitemid",checkitemid);
                groupDao.setRelationForGroupAndItem(map);
            }
        }
        //更新checkgroup
        groupDao.updateCheckGroup(checkGroup);

    }

    /**
     * findAll Groups
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAllGroup() {
        return groupDao.findAllGroups();
    }


    public void setGroupAndItem(CheckGroup checkGroup, Integer[] checkitemIds){
        if (checkitemIds.length>0 && checkitemIds!=null) {
            Integer checkGroupId = checkGroup.getId();
            Map<String,Object> map = new HashMap<>();
            for (Integer checkitemId : checkitemIds) {
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                groupDao.addGroupAndItem(map);
            }
        }
    }
}