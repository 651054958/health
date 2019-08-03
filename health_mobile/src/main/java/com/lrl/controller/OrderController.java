package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Order;
import com.lrl.pojo.Package;
import com.lrl.service.OrderService;
import com.lrl.service.PackageService;
import com.lrl.utils.SMSUtils;
import com.lrl.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/26 13:03
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result fun(@RequestBody Map<String,String> orderInfo){
        String validateCode = orderInfo.get("validateCode");
        String telephone = orderInfo.get("telephone");
        Jedis jedis = jedisPool.getResource();
        String key = telephone +"_"+ SMSUtils.VALIDATE_CODE;
        String value_redis = jedis.get(key);
        if (value_redis==null) {
            return new Result(false,MessageConstant.VALIDATECODE_TIMEOUT_OR_NOT_SENDCODE);
        }
        if (!value_redis.equals(validateCode)) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        orderService.addorder(orderInfo);
        return new Result(true,MessageConstant.ORDER_SUCCESS);
    }
}