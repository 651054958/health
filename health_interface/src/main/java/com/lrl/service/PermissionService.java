package com.lrl.service;

import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAllPermissions();

    Integer[] findPermissionIdsByRoleId(Integer id);

    PageResult findPage(QueryPageBean pageBean);

    void add(Permission permission) throws HealthException;

    Permission findById(Integer id);

    void deleteById(Integer id) throws HealthException;
}
