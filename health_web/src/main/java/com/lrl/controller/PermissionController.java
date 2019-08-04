package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.pojo.Permission;
import com.lrl.service.PermissionService;
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
 * @date 2019/8/3 16:27
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    @RequestMapping("/findAllPermissions")
    public Result findAllPermissions(){
        try {
            List<Permission> permissionList = permissionService.findAllPermissions();
            return new Result(true, MessageConstant.GET_SUCCESS,permissionList);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }

    @RequestMapping("/findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(Integer id){
        try {
            Integer[] ids = permissionService.findPermissionIdsByRoleId(id);
            return new Result(true, MessageConstant.GET_SUCCESS,ids);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }

    /**
     * permission分页查询操作
     * @return
     */
    @RequestMapping("/findPage")
    public Result getUserInfoList(@RequestBody QueryPageBean pageBean) {
        try {
            PageResult pageResult = permissionService.findPage(pageBean);
            return new Result(true, MessageConstant.GET_FAILED, pageResult);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_SUCCESS);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return new Result(true, MessageConstant.ADD_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_FAILED);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Permission permission = permissionService.findById(id);
            return new Result(true, MessageConstant.GET_SUCCESS,permission);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_FAILED);
        }
    }
}