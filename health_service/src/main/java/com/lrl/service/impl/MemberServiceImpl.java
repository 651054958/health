package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.dao.MemberDao;
import com.lrl.pojo.Member;
import com.lrl.service.MemberService;
import com.lrl.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
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

}