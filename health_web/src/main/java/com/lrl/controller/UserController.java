package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Menu;
import com.lrl.pojo.Role;
import com.lrl.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/31 10:01
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername(){
        //security user
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * find all permissions
     * @return
     */
    @PostMapping("/findUserPage")
    public Result findUserPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult userPage = userService.findUserPage(queryPageBean);
            return new Result(true,MessageConstant.GET_USERLIST_SUCCESS,userPage);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.GET_USERLIST_FAILED);
        }
    }

    @GetMapping("/findRole")
    public Result findRole(){
        try {
            List<Role>list= userService.findRole();
            return new Result(true,MessageConstant.GET_ROLES_SUCCESS,list);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.GET_ROLES_FAILED);
        }
    }

    @GetMapping("/findById")
    public Result findById(int id){
        try {
            Map<String,Object> map= userService.findById(id);
            map.put("password","");
            return new Result(true,MessageConstant.GET_USER_SUCCESS,map);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.GET_USER_FAILED);
        }
    }

    @PostMapping("/delete")
    public Result delete(int id){
        try {
            userService.delete(id);
            return new Result(true,MessageConstant.DELETE_SUCCESS);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.DELETE_FAILED);
        }
    }
    @PostMapping("/addUser")
    public Result addUser(@RequestBody com.lrl.pojo.User user, Integer[] roleIds){
        try {
            userService.addUser(user,roleIds);
            return new Result(true,MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.ADD_USER_FAILED);
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody com.lrl.pojo.User user, Integer[] roleIds){
        try {
            userService.update(user,roleIds);
            return new Result(true,MessageConstant.UPDATE_SUCCESS);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.UPDATE_FAILED);
        }
    }

    @GetMapping("/getMenu")
    public Result getMenu(HttpServletRequest request){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            List<Menu>result= userService.getMenu(username);
            return new Result(true,MessageConstant.GET_MENU_SUCCESS,result);
        } catch (Exception e) {
            throw new HealthException(MessageConstant.GET_MENU_FAIL);
        }
    }


}