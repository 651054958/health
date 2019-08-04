package com.lrl.jobs;

import com.lrl.constant.RedisConstant;
import com.lrl.dao.OrderSettingDao;
import com.lrl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/4 9:07
 */
public class CleanOrderSetting {
    @Autowired
    private OrderSettingDao orderSettingDao;

    public void cleanOrderSetting() {
        System.out.println("cleanOrderSetting()...");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //最近需要清理的日期
        String cleanEndDay = sdf.format(calendar.getTime());
        orderSettingDao.deleteBeforeDay(cleanEndDay);
    }
}