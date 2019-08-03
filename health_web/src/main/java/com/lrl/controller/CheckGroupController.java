package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.entity.Result;
import com.lrl.pojo.CheckGroup;
import com.lrl.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:检查组控制器
 * \
 * @date 2019/7/20 21:02
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService groupService;


    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean pageBean){
        Result result = new Result(true);
        //todo: find resultPage
        try {
            PageResult pageResult = groupService.findPage(pageBean);
            result.setData(pageResult);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }


    @GetMapping("/delete")
    public Result deleteById(Integer id){
        Result result = new Result(true);
        try {
            //todo: check group depended
            groupService.checkCountFromItemAndGroupById(id);

            //todo: delete group by id
            groupService.deleteById(id);
            result.setData(MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(e.getMessage());
        }
        return result;

    }

    @RequestMapping("/addGroup")
    public Result addGroup(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        Result result = new Result(true);
        System.out.println("get group info"+checkGroup);
        try {
            groupService.addGroup(checkGroup,checkitemIds);
            result.setData(MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return result;
    }


    @RequestMapping("/findById")
    public Result findById(int id){
            Result result = new Result(true);
        try {
            CheckGroup checkGroup = groupService.findById(id);
            result.setData(checkGroup);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKGROUP_FAIL);
            }
        return result;
    }

    @RequestMapping("/findItemsById")
    public Result findItemsById(@RequestParam("id") int id){
        Result result = new Result(true);
        try {
            Integer[] ids = groupService.findItemsById(id);
            result.setData(ids);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.FIND_ITEMS_ERROR);
        }
        return result;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,@RequestParam("checkitemids") Integer[] checkitemids){
        Result result = new Result(true);
        try {
            groupService.update(checkGroup,checkitemids);
            result.setData(MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return result;
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> groups = null;
        try {
            groups = groupService.findAllGroup();
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return new Result(true,"",groups);
    }

}