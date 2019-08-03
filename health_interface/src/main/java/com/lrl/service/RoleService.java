package com.lrl.service;

import com.lrl.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    Integer[] findRoleIdsByUserId(Integer userId);
}
