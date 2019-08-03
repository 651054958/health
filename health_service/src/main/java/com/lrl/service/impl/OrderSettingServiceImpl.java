package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.dao.OrderSettingDao;
import com.lrl.pojo.OrderSetting;
import com.lrl.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/24 20:09
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * add oderSettingList
     *
     * @param orderSettingList
     */
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList.size()>0 && orderSettingList!=null) {
            for (OrderSetting orderSetting : orderSettingList) {
                int count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count>0) {
                    orderSettingDao.update(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * findOrderSettingByMonth
     *
     * @param month
     * @return
     */
    @Override
    public List<OrderSetting> findOrderSettingByMonth(String month) {
        String firstDay = month+"-1";
        String endDay = month+"-31";
        return orderSettingDao.findOrderSettingByMonth(firstDay,endDay);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count>0) {
            orderSettingDao.update(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }
}