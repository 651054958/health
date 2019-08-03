package com.lrl.service;

import com.lrl.pojo.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<User> findAll();

}
