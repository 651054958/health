package com.lrl.dao;

import com.lrl.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotPackage();

    Integer findCntByDayFinish(String today);

    /**
     * findOrderCountBetween
     * @param thisMonday
     * @param sunday
     * @return
     */
    Integer findOrderCountBetween(@Param("start") String thisMonday, @Param("end") String sunday);

    Integer findVisitsCountBetween(@Param("begin") String thisMonday, @Param("end") String today);
}
