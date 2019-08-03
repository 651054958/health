package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.dao.MemberDao;
import com.lrl.dao.OrderDao;
import com.lrl.service.ReportService;
import com.lrl.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/31 21:19
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    /**
     * getBusinessReportData
     *
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        String thisMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());

        String sunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());

        String lastDayOfMonth = DateUtils.parseDate2String(DateUtils.getLastDayOfMonth());

        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        //todo: member count info
        //本日新增 *
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        //本日之前数量 <=
        Integer cntBeforeToday = memberDao.findMemberCountBeforeDate(today);
        //总数 *
        Integer totalMember = cntBeforeToday;
        //cnt before monday
        Integer cntBeforeMonday = memberDao.findMemberCountBeforeDate(thisMonday);
        //本周新增 *
        Integer thisWeekNewMember = cntBeforeToday-cntBeforeMonday;

        //本月新增 *
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);

        //todo : order count info
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);

        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);

        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetween(thisMonday,sunday);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountBetween(thisMonday,today);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountBetween(firstDay4ThisMonth,lastDayOfMonth);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountBetween(firstDay4ThisMonth,today);

        //todo: map info input
        Map<String,Object> map = new HashMap<>();
        //member
        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        //order
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        return map;
    }
}