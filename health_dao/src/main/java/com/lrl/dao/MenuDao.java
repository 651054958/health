package com.lrl.dao;

import com.lrl.pojo.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> findAllMenus();

    Integer[] findMenuIdsByRoleId(Integer id);
}
