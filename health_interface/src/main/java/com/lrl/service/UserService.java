package com.lrl.service;

import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Menu;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findUserByUsername(String username);

    PageResult findUserPage(QueryPageBean queryPageBean);

    List<Role> findRole();

    Map<String,Object> findById(int id);

    void delete(int id);


    List<Menu> getMenu(String username);

    void addUser(User user, Integer[] roleIds);

    void update(User user, Integer[] roleIds);
}
