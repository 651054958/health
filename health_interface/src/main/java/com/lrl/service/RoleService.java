package com.lrl.service;

import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    Integer[] findRoleIdsByUserId(Integer userId);

    PageResult findPage(QueryPageBean pageBean);

    void addRole(Integer[] permissionIds, Integer[] menuIds, Role role);

    Role findById(Integer id);

    void deleteById(Integer id) throws HealthException;

    void update(Role role, Integer[] permissionIds, Integer[] menuIds);
}
