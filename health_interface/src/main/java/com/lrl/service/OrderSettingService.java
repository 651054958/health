package com.lrl.service;

import com.lrl.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {
    /**
     * add oderSettingList
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * findOrderSettingByMonth
     * @param month
     * @return
     */
    List<OrderSetting> findOrderSettingByMonth(String month);

    void editNumberByDate(OrderSetting orderSetting);
}
