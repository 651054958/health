package com.lrl.dao;

import com.lrl.pojo.User;

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

}
