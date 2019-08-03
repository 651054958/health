package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.UserDao;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;
import com.lrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/29 11:53
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUsername(String username) {

        return userDao.findUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<Role> findAllRoleIds() {
        return userDao.findAllRoles();
    }

    @Override
    @Transactional
    public void addUser(Integer[] roleIds, User user) throws HealthException{
        //checkUsername
        User userByUsername = userDao.findUserByUsername(user.getUsername());
        if (userByUsername!=null) {
            throw new HealthException(MessageConstant.USERNAME_EXIST_ERROR);
        }
        userDao.addUser(user);
        Integer userId = user.getId();
        for (Integer roleId : roleIds) {
            userDao.addUserRoles(roleId,userId);
        }
    }

    /**
     * findPage
     *
     * @param pageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        if (!StringUtils.isEmpty(pageBean.getQueryString())) {
            pageBean.setQueryString("%"+pageBean.getQueryString()+"%");
        }
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<User> page = userDao.findByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public User findById(Integer id) {
        User userById = userDao.findUserById(id);
        userById.setPassword("");
        return userById;
    }

    @Override
    @Transactional
    public void update(User user, Integer[] roleIds) {
        String password = user.getPassword();
        if (password==null || password.length()==0 ||password=="") {
            User userById = userDao.findUserById(user.getId());
            user.setPassword(userById.getPassword());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.update(user);
        Integer userId = user.getId();
        userDao.deleteRolesById(userId);
        for (Integer roleId : roleIds) {
            userDao.addUserRoles(roleId,userId);
        }
    }

    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }
}