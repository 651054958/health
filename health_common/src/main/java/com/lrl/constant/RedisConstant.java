package com.lrl.constant;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/24 13:52
 */
public class RedisConstant {
    public static final String SETMEAL_PIC_RESOURCES = "health_pic_in_redis";
    public static final String SETMEAL_PIC_DB_RESOURCES = "health_pic_in_db";
    //用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_ORDER = "001";
    //用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_LOGIN = "002";
    //用于缓存找回密码时发送的验证码
    public static final String SENDTYPE_GETPWD = "003";
    /**
     * 测试的timeout
     */
    public static final int TIMEOUT_TEST = 60000;
    /**
     * 上线的timeout
     */
    public static final int TIMEOUT_RELEASE = 120;
}