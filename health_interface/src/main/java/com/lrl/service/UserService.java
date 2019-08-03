package com.lrl.service;

import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<User> findAll();

    List<Role> findAllRoleIds();

    void addUser(Integer[] roleIds, User user) throws HealthException;

    /**
     * findPage
     * @param pageBean
     * @return
     */
    PageResult findPage(QueryPageBean pageBean);

    User findById(Integer id);

    void update(User user, Integer[] roleIds);

    void deleteById(Integer id);
}
