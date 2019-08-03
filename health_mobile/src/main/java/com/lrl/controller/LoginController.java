package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MemberConstant;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.pojo.Member;
import com.lrl.service.MemberService;
import com.lrl.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/27 17:20
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result checkTelAndValidateCode(HttpServletResponse response, @RequestBody Map<String,String> map){
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        if (!(null!=telephone&&null!=validateCode&&telephone.length()>0&&validateCode.length()>0)) {
            return new Result(false, MessageConstant.ILLEGAL_INPUT);
        }
        String key = telephone+"_"+SMSUtils.LOGIN_VALIDATE;
        Jedis jedis = jedisPool.getResource();
        String login_validate_in_redis = jedis.get(key);
        if (login_validate_in_redis==null) {
            return new Result(false,MessageConstant.VALIDATECODE_TIMEOUT_OR_NOT_SENDCODE);
        }
        if (!(login_validate_in_redis.equals(validateCode))) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        Member member = memberService.findByTelephone(telephone);
        if (null==member) {
            //no -> add member
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }

        //用户跟踪
        Cookie cookie = new Cookie(MemberConstant.LOGIN_MEMBER_TEL,telephone);
        cookie.setMaxAge(MemberConstant.COOKIE_MAX_AGE_ONE_MONTH);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new Result(true,MessageConstant.LOGIN_SUCCESS,member);
    }
}