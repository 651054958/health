package com.lrl.service;

import com.lrl.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findAllMenus();

    Integer[] findMenuIdsByRoleId(Integer id);
}
