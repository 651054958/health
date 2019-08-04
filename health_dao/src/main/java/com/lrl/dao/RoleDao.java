package com.lrl.dao;

import com.github.pagehelper.Page;
import com.lrl.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:36
 */
public interface RoleDao {
    List<Role> findAllRoleIds();

    /**
     * findRoleIdsByUserId
     * @param userId
     * @return
     */
    Integer[] findRoleIdsByUserId(Integer userId);

    Page<Role> findByCondition(String queryString);

    void add(Role role);

    void addPermissionAndRole(@Param("role_id") Integer roleId, @Param("permission_id") Integer permissionId);

    void addMenuAndRole(@Param("role_id")Integer roleId, @Param("menu_id") Integer menuId);

    Role findById(Integer id);

    int findRelationCntbYRoleId(Integer id);

    void deleteById(Integer id);

    void update(Role role);

    void deletePermissionAndRoleByRoleId(Integer roleId);

    void deleteMenuAndRoleByRoleId(Integer roleId);
}