package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.pojo.CheckItem;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lrl.constant.MessageConstant;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/19 10:08
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService itemService;

    @RequestMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result add(@RequestBody CheckItem checkItem){
        System.out.println("add operation running");
        System.out.println(checkItem);

        Result result = new Result(true);
        try {
            itemService.add(checkItem);
            result.setData(MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return result;
    }


    @RequestMapping("/findPage")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean pageBean){
        System.out.println("get pageBean is ----"+pageBean);
        Result result = new Result(true);
        try {
            PageResult pageResult = itemService.queryPage(pageBean);
            result.setData(pageResult);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        System.out.println("result--"+result);
        return result;
    }


    @RequestMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result deleteById(Integer id){
        Result result = new Result(true);
        //todo:check if this item has been depended--get count from tab t_checkgroup_checkitem by id
        try {
            itemService.getCountFromGroupAndItemById(id);
            itemService.deleteItemById(id);
            result.setData(MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e){
            result.setFlag(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/findItemById")
    public Result findItemById(Integer id){
        Result result = new Result(true);
        try {
            CheckItem item = itemService.findItemById(id);
            result.setData(item);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.SERVICE_ERROR);
        }
        return result;

    }


    @RequestMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result update(@RequestBody CheckItem checkItem){
        System.out.println("update get item--"+checkItem);
        Result result = new Result(true);
        try {
            itemService.update(checkItem);
            result.setData(MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return result;
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        Result result = new Result(true);
        try {
            List<CheckItem> items = itemService.findAll();
            result.setData(items);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return result;
    }
}