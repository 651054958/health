package com.lrl.dao;

import com.github.pagehelper.Page;
import com.lrl.pojo.Menu;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    Page<Map<String,Object>> findPage(@Param("queryString") String  queryString);

    List<Role> findRole();

    Map<String,Object> findById(int id);

    void delete(int id);

    void delete_user_role(int id);

    void add_user_role(@Param("user_id") Integer user_id,@Param("role_id") Integer role_id);

    void addUser(User user);

    void update(User user);

    int getIdByUsername(String username);

    List<Menu> getMenu(int id);

    Integer[] getRoleIdByUserId(Integer id);

    User findOneById(Integer user_id);
}
