package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Role;
import com.lrl.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 分页查询操作
     * @return
     */
    @RequestMapping("/findPage")
    public Result getUserInfoList(@RequestBody QueryPageBean pageBean) {
        try {
            PageResult pageResult = userService.findPage(pageBean);
            return new Result(true, MessageConstant.GET_ROLES_SUCCESS, pageResult);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_ROLES_FAILED);
        }
    }

    /**
     * find all permissions
     * @return
     */
    @RequestMapping("/findAllRoles")
    public Result findAllRoles(){
        try {
            List<Role> roles = userService.findAllRoleIds();
            return new Result(true,MessageConstant.GET_ROLES_SUCCESS,roles);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_ROLES_FAILED);
        }
    }

    @RequestMapping("/addUser")
    public Result fun(@RequestBody com.lrl.pojo.User user,Integer[] roleIds){
        if (!(roleIds.length>0&&roleIds!=null)) {
            throw new HealthException(MessageConstant.MUST_CHOOSE_ONE_ROLE);
        }
        if (user.getTelephone().length()!=11) {
            throw new HealthException(MessageConstant.ERROR_TELEPHONE_LENGTH);
        }
        try {
            userService.addUser(roleIds,user);
            return new Result(true,MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_USER_FAILED);
        }
    }

    @RequestMapping("/findById")
    public Result fun(Integer id){
        try {
            com.lrl.pojo.User user = userService.findById(id);
            return new Result(true,MessageConstant.GET_USER_SUCCESS,user);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_USER_FAILED);
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody com.lrl.pojo.User user,Integer[] roleIds){

        try {
            userService.update(user,roleIds);
            return new Result(true,MessageConstant.SUCCESS_UPDATE_USER);
        } catch (Exception e) {
            return new Result(false,MessageConstant.FAILED_UPDATE_USER);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            userService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_FAILED);
        }
    }
}