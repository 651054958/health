package com.lrl.jobs;

import com.lrl.constant.RedisConstant;
import com.lrl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/24 13:49
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        System.out.println("clearImg()...");

        //1.计算set的差值
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //2.遍历集合
        for (String pic : set) {
            //3.删除
            //3.1 删除七牛空间上的
            QiniuUtils.deleteFileFromQiniu(pic);
            //3.2删除redis集合里面的
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);

        }
    }
}