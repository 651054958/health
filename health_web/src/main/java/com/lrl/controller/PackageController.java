package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.constant.RedisConstant;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.pojo.Package;
import com.lrl.service.PackageService;
import com.lrl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/22 20:48
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private PackageService packageService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean pageBean){
        PageResult pageResult = null;
        try {
            pageResult = packageService.findPage(pageBean);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true,"",pageResult);
    }

    /**
     * upload file
     * "hamburger".substring(4, 8) returns "urge"
     * "smiles".substring(1, 5) returns "mile"
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        try {
            String originalFileName = imgFile.getOriginalFilename();
            String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
            System.out.println("suffixName--"+suffixName);
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString()+suffixName;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //jedis add
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            Map<String,String> map = new HashMap<>();
            map.put("domain",QiniuUtils.domain);
            map.put("fileName",fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Package pkg,@RequestParam("groupIds") Integer[] groupIds){
        try {
            packageService.add(pkg,groupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pkg.getImg());
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
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

    @RequestMapping("/findGroupIdsByPackageId")
    public Result findGroupIdsByPackageId(Integer packageId){
        try {
            Integer[] ids = packageService.findGroupIdsByPackageId(packageId);
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,ids);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/update")
    public Result fun(@RequestBody Package pkg,@RequestParam("groupIds") Integer[] groupIds){
        try {
            packageService.update(pkg,groupIds);
            return new Result(true,MessageConstant.EDIT_PACKAGE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_PACKAGE_FAILED);
        }
    }
}