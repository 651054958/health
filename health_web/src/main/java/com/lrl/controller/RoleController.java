package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.Role;
import com.lrl.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:26
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;

    @RequestMapping("/findAllRoles")
    public Result findAllRoles(){
        try {
            List<Role> roles = roleService.findAllRoles();
            return new Result(true, MessageConstant.GET_ROLES_SUCCESS,roles);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_ROLES_FAILED);
        }
    }

    /**
     *
     * @param userId
     * @return ids[]
     */
    @RequestMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer userId){
        Integer[] roleIds = roleService.findRoleIdsByUserId(userId);
        return new Result(true,MessageConstant.GET_ROLEIDS_SUCCESS,roleIds);
    }
}