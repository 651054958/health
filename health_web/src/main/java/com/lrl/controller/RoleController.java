package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.pojo.Role;
import com.lrl.service.RoleService;
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

    @RequestMapping("/findPage")
    public Result fun(@RequestBody QueryPageBean pageBean){
        PageResult pageResult = roleService.findPage(pageBean);
        return new Result(true,MessageConstant.FIND_ROLE_PAGE_SUCCESS,pageResult);
    }

    @RequestMapping("/addRole")
    public Result addRole(@RequestBody Role role, Integer[] permissionIds,Integer[] menuIds){
        try {
            roleService.addRole(permissionIds,menuIds,role);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_USER_FAILED);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Role role = roleService.findById(id);
            return new Result(true, MessageConstant.GET_SUCCESS,role);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            roleService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_FAILED);
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Role role, Integer[] permissionIds,Integer[] menuIds){
        try {
            roleService.update(role,permissionIds,menuIds);
            return new Result(true, MessageConstant.UPDATE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.UPDATE_FAILED);
        }
    }
}