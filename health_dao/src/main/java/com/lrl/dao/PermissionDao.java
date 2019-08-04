package com.lrl.dao;

import com.github.pagehelper.Page;
import com.lrl.pojo.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> findAllPermissions();

    Integer[] findPermissionIdsByRoleId(Integer id);

    Page<Permission> findByCondition(String queryString);

    Integer findByKeyword(String keyword);

    void add(Permission permission);

    Permission findById(Integer id);

    Integer findRelationCntById(Integer id);

    void deleteById(Integer id);
}
