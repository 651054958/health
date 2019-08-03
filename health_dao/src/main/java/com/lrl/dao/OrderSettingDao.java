package com.lrl.dao;

import com.lrl.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    /**
     * add orderSetting
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * findCountByOrderDate
     * @param orderDate
     * @return
     */
    int findCountByOrderDate(Date orderDate);

    /**
     * update orderSetting
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);


    /**
     * findOrderSettingByMonth
     * @param firstDay
     * @param endDay
     * @return
     */
    List<OrderSetting> findOrderSettingByMonth(@Param("firstDay") String firstDay,@Param("endDay") String endDay);

    /**
     *
     * @param orderDate
     * @return
     */
    OrderSetting findOrderSettingByDay(String orderDate);

    /**
     * addReservationByOrderdate
     * @param orderDate
     */
    void addReservationByOrderdate(String orderDate);

}
