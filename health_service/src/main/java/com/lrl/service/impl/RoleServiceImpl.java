package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.dao.RoleDao;
import com.lrl.pojo.Role;
import com.lrl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:29
 */
@Service
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
}