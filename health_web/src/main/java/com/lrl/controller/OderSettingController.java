package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.OrderSetting;
import com.lrl.service.OrderSettingService;
import com.lrl.utils.POIUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/24 19:59
 */
@RestController
@RequestMapping("/ordersetting")
public class OderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result update(MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list.size()>0 && list!=null) {
//                OrderSetting orderSetting = new OrderSetting();
                List<OrderSetting> oderSettingList = new ArrayList<>();
                //add info into oder_list
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                for (String[] strings : list) {
                    oderSettingList.add(new OrderSetting(df.parse(strings[0]),Integer.parseInt(strings[1])));
                }
                orderSettingService.add(oderSettingList);
            }

        } catch (IOException e) {
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        } catch (ParseException e) {
            return new Result(false, MessageConstant.PARSE_DATE_ERROR);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

    }

    /**
     *
     * @param month 代表当前的年月: 2019-07
     * @return
     */
    @RequestMapping("/findOrderSetting")
    public Result findOrderSetting(String month){
        try {
            List<OrderSetting> list = orderSettingService.findOrderSettingByMonth(month);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     *
     * @param orderDate 代表预约的日期（2019-07-23）
     * @param number 代表预约数
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(String orderDate,Integer number){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = df.parse(orderDate);
            OrderSetting orderSetting = new OrderSetting(parse, number);

            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (ParseException e) {
            return new Result(false,MessageConstant.EDIT_ORDERSETTING_FAILED);
        }
    }
}