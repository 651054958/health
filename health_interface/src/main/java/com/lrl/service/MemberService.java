package com.lrl.service;

import com.lrl.pojo.Member;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MemberService {
    /**
     * login check
     * @param telephone
     * @return member info
     */
    Member check(HttpServletResponse response, String telephone);

    /**
     * find member by tel
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * add member , update this meber info include last_insert_id
     * @param member
     */
    void add(Member member);

    /**
     * find member cnt list by month list
     * @param monthList
     * @return
     */
    List<Integer> findMemberCntByMonthList(List<String> monthList);

    /**
     * get member cnt report
     * @return
     */
    Map<String,Object> getMemberReport();


}
