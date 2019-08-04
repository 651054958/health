package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.UserDao;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Menu;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;
import com.lrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findUserPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Map<String,Object>> page=userDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Role> findRole() {
        return userDao.findRole();
    }

    @Override
    public Map<String,Object> findById(int id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        //删除用户需将其联系t_user_role一同删除
        userDao.delete_user_role(id);
        userDao.delete(id);
    }



    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Map<String, String> map) {
        Integer role_id = Integer.parseInt((String) map.get("role_id"));
        Integer user_id = Integer.parseInt( map.get("id"));
        userDao.delete_user_role(user_id);
        userDao.add_user_role(user_id,role_id);
        userDao.update(map);
    }*/

    @Override
    public List<Menu> getMenu(String username) {
        User user = userDao.findUserByUsername(username);
        Integer id = user.getId();
        Integer[] roleIds=userDao.getRoleIdByUserId(id);
        List<Menu> list = new ArrayList<>();
        for (Integer roleId : roleIds) {
            List<Menu> list1 = userDao.getMenu(roleId);
            for (Menu menu : list1) {
                list.add(menu);
            }
        }
        return list;
    }

    @Override
    public void addUser(User user, Integer[] roleIds) {
        if (roleIds.length==0 || roleIds==null) {
            throw new HealthException(MessageConstant.ILLEGAL_INPUT);
        }
        userDao.addUser(user);
        Integer userId = user.getId();
        for (Integer roleId : roleIds) {
            userDao.add_user_role(userId,roleId);
        }
    }

    @Override
    public void update(User user, Integer[] roleIds) {
        Integer user_id = user.getId();
        String password = user.getPassword();
        if (password==null ||password=="") {
            User query_User = userDao.findOneById(user_id);
            user.setPassword(query_User.getPassword());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.delete_user_role(user_id);
        for (Integer roleId : roleIds) {
            userDao.add_user_role(user_id,roleId);
        }
        userDao.update(user);
    }

}