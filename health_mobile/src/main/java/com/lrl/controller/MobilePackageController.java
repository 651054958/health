package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lrl.constant.MessageConstant;
import com.lrl.constant.RedisConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.Package;
import com.lrl.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/getPackage")
    public Result findPage(){

        try {
            Jedis jedis = jedisPool.getResource();
            String redis_packageList = jedis.get(RedisConstant.REDIS_PACKAGE);
            if (redis_packageList==null ||redis_packageList=="") {
                List<Package> packageList = packageService.findAll();
                String db_packageList = JSON.toJSONString(packageList);
                jedis.setex(RedisConstant.REDIS_PACKAGE,RedisConstant.TIMEOUT_TEST,db_packageList);
                return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,packageList);
            }
            List<Package> parse = (List<Package>) JSON.parse(redis_packageList);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,parse);
//            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
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