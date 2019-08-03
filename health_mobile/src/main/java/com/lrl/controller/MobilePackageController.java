package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.Package;
import com.lrl.service.PackageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/25 13:10
 */
@RestController
@RequestMapping("/package")
public class MobilePackageController {
    @Reference
    private PackageService packageService;

    @RequestMapping("/getPackage")
    public Result findPage(){
        try {
            List<Package> packageList = packageService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,packageList);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/findPackageById")
    public Result findPackageById(Integer packageId){
        Package pkg = null;
        try {
            pkg = packageService.findPackageById(packageId);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pkg);
    }

    @RequestMapping("/findSimplePackageInfoById")
    public Result findSimplePackageInfo(Integer packageId){
        try {
            Package packageById = packageService.findSimplePackageInfo(packageId);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,packageById);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}