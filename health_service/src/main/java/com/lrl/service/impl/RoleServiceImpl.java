package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.RoleDao;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.Role;
import com.lrl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:29
 */
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoleIds();
    }

    @Override
    public Integer[] findRoleIdsByUserId(Integer userId) {
        return roleDao.findRoleIdsByUserId(userId);
    }

    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        if (!StringUtils.isEmpty(pageBean.getQueryString())) {
            pageBean.setQueryString("%"+pageBean.getQueryString()+"%");
        }
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<Role> page = roleDao.findByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    public void addRole(Integer[] permissionIds, Integer[] menuIds, Role role) {
        if (permissionIds==null || permissionIds.length==0) {
            throw new HealthException(MessageConstant.PARAM_ERROR);
        }
        if (menuIds==null || menuIds.length==0) {
            throw new HealthException(MessageConstant.PARAM_ERROR);
        }
        //add role
        roleDao.add(role);
        Integer roleId = role.getId();
        //add relation
        for (Integer permissionId : permissionIds) {
            roleDao.addPermissionAndRole(roleId,permissionId);
        }
        for (Integer menuId : menuIds) {
            roleDao.addMenuAndRole(roleId,menuId);
        }
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public void deleteById(Integer id) throws HealthException{
        //relation check
        int cnt = roleDao.findRelationCntbYRoleId(id);
        if (cnt>0) {
            throw new HealthException(MessageConstant.DELETE_FAILED_DEPENDENCE);
        }
        roleDao.deleteById(id);

        //if true delete
    }

    @Override
    @Transactional
    public void update(Role role, Integer[] permissionIds, Integer[] menuIds) {
        if (permissionIds==null || permissionIds.length==0) {
            throw new HealthException(MessageConstant.PARAM_ERROR);
        }
        if (menuIds==null || menuIds.length==0) {
            throw new HealthException(MessageConstant.PARAM_ERROR);
        }
        //update role
        roleDao.update(role);
        Integer roleId = role.getId();
        //delete relation
        roleDao.deletePermissionAndRoleByRoleId(roleId);
        roleDao.deleteMenuAndRoleByRoleId(roleId);
        //update permission
        for (Integer permissionId : permissionIds) {
            roleDao.addPermissionAndRole(roleId,permissionId);
        }
        //update menu
        for (Integer menuId : menuIds) {
            roleDao.addMenuAndRole(roleId,menuId);
        }
    }


}