package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @RequestMapping("/getUserInfoList")
    public Result getUserInfoList(){
        try {
            List<com.lrl.pojo.User> userList = userService.findAll();
            return new Result(true,MessageConstant.GET_USERLIST_SUCCESS,userList);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_USERLIST_FAILED);
        }
    }

    /**
     * find all permissions
     * @return
     */
    @RequestMapping("/findAll")
    public Result fun(){

        return new Result();
    }
}