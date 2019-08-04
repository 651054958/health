package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.PermissionDao;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Permission;
import com.lrl.pojo.User;
import com.lrl.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:30
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public List<Permission> findAllPermissions() {
        return permissionDao.findAllPermissions();
    }

    @Override
    public Integer[] findPermissionIdsByRoleId(Integer id) {
        return permissionDao.findPermissionIdsByRoleId(id);
    }

    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        if (!StringUtils.isEmpty(pageBean.getQueryString())) {
            pageBean.setQueryString("%"+pageBean.getQueryString()+"%");
        }
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<Permission> page = permissionDao.findByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Permission permission) throws HealthException{
        String keyword = permission.getKeyword();
        if (keyword==null) {
            throw new HealthException(MessageConstant.ILLEGAL_INPUT);
        }
        permission.setKeyword(keyword.toUpperCase());
        Integer cnt = permissionDao.findByKeyword(permission.getKeyword());
        if (cnt>0) {
            throw new HealthException(MessageConstant.READD_PERMISSION);
        }
        permissionDao.add(permission);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public void deleteById(Integer id) throws HealthException{
        Integer cnt = permissionDao.findRelationCntById(id);
        if (cnt>0) {
            throw new HealthException(MessageConstant.DELETE_ERROR_DEPENDENCE);
        }
        permissionDao.deleteById(id);
    }
}