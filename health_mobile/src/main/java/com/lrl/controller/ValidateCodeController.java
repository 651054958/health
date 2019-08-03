package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.lrl.constant.MemberConstant;
import com.lrl.constant.MessageConstant;
import com.lrl.constant.RedisConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.Member;
import com.lrl.service.MemberService;
import com.lrl.service.PackageService;
import com.lrl.utils.SMSUtils;
import com.lrl.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/26 17:00
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private PackageService packageService;

    @Reference
    private MemberService memberService;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            //redis key
            String key = telephone+"_"+ SMSUtils.VALIDATE_CODE;
            Jedis resource = jedisPool.getResource();
            String redis_value = resource.get(key);
            if (redis_value!=null) {
                return new Result(false, MessageConstant.RESEND_VALIDATECODE);
            }
            //send code
            SMSUtils.sendShortMessage("SMS_171355599",String.valueOf(telephone),code.toString());
            resource.setex(key,300,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        String key = telephone+"_"+SMSUtils.LOGIN_VALIDATE;
        Jedis jedis = jedisPool.getResource();
        String value_in_redis = jedis.get(key);
        if (null!=value_in_redis) {
            return new Result(false,MessageConstant.RESEND_VALIDATECODE);
        }
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.LOGIN_VALIDATE,String.valueOf(telephone),code.toString());
            //test time out 60000s
            jedis.setex(key, RedisConstant.TIMEOUT_TEST,code.toString());
        } catch (ClientException e) {
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}