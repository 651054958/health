package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.dao.MenuDao;
import com.lrl.pojo.Menu;
import com.lrl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:32
 */
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<Menu> findAllMenus() {
        return menuDao.findAllMenus();
    }

    @Override
    public Integer[] findMenuIdsByRoleId(Integer id) {
        return menuDao.findMenuIdsByRoleId(id);
    }
}