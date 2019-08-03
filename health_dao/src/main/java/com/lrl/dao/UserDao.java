package com.lrl.dao;

import com.github.pagehelper.Page;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * find user by username
     * @param username
     * @return user detail include roles and permissions
     */
    User findUserByUsername(String username);

    /**
     * find all users
     * @return
     */
    List<User> findAll();

    /**
     * findAllRoles
     * @return
     */
    List<Role> findAllRoles();

    /**
     * add user base info
     * @param user
     */
    void addUser(User user);

    /**
     * add userRoles
     * @param roleId
     * @param userId
     */
    void addUserRoles(@Param("role_id") Integer roleId, @Param("user_id") Integer userId);

    /**
     * findByCondition
     * @param queryString
     * @return
     */
    Page<User> findByCondition(String queryString);

    User findUserById(Integer id);
}
