package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.MemberDao;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Member;
import com.lrl.service.MemberService;
import com.lrl.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/27 13:44
 */
@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;

    /**
     * login check
     *
     * @param telephone
     * @return member info
     */
    @Override
    public Member check(HttpServletResponse response, String telephone) {
        //exist this member?

        return null;
    }

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    /**
     * find member cnt list by month list
     *
     * @param monthList
     * @return
     */
    @Override
    public List<Integer> findMemberCntByMonthList(List<String> monthList) {
        List<Integer> list = new ArrayList<>();
        /*monthList.forEach(month->{
            month = month.replaceAll(".","-")+"-31";
            Integer cnt = memberDao.findMemberCountBeforeDate(month);
            list.add(cnt);
        });*/
        for (String month : monthList) {
            month = month.replace(".","-")+"-31";
            Integer cnt = memberDao.findMemberCountBeforeDate(month);
            list.add(cnt);
        }
        return list;
    }

    /**
     * get member cnt report
     *
     * @return
     */
    @Override
    public Map<String, Object> getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List<String> monthList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            monthList.add(sdf.format(calendar.getTime()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("months",monthList);
        List<Integer> memberCntList = findMemberCntByMonthList(monthList);
        map.put("memberCntList",memberCntList);
        return map;
    }

    @Override
    public Map<String, Object> getCustomReport(String[] customDay) throws HealthException {
        String start = customDay[0];
        String end = customDay[1];
        String now = null;
        try {
            now = DateUtils.parseDate2String(new Date());
        } catch (Exception e) {
            throw new HealthException(MessageConstant.PARSE_ERROR);
        }
        //传参的月份差
        int result = 0;
        //现在和开始时间的月份差
        int result_now = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(start));
            c2.setTime(sdf.parse(end));
            c3.setTime(sdf.parse(now));
        } catch (ParseException e) {
            throw new HealthException(MessageConstant.PARSE_ERROR);
        }
        //月份差
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        result_now = c3.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        //final
        result_now = result_now == 0? 1: Math.abs(result_now);
        result = result == 0 ? 1 : Math.abs(result);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-result_now);
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < result_now; i++) {
            calendar.add(Calendar.MONTH,1);
            monthList.add(sdf.format(calendar.getTime()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("months",monthList);
        List<Integer> memberCntList = findMemberCntByMonthList(monthList);
        map.put("memberCntList",memberCntList);
        return map;
    }

}