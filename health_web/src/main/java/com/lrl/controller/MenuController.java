package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.Menu;
import com.lrl.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:28
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    @RequestMapping("/findAllMenus")
    public Result findAllMenus(){
        try {
            List<Menu> menuList = menuService.findAllMenus();
            return new Result(true, MessageConstant.GET_SUCCESS,menuList);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }

    @RequestMapping("/findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(Integer id){
        try {
            Integer[] ids = menuService.findMenuIdsByRoleId(id);
            return new Result(true, MessageConstant.GET_SUCCESS,ids);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }
}