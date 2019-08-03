package com.lrl.dao;

import com.lrl.pojo.Role;

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
}