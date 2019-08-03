package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lrl.constant.MessageConstant;
import com.lrl.dao.MemberDao;
import com.lrl.dao.OrderDao;
import com.lrl.dao.OrderSettingDao;
import com.lrl.exception.HealthException;
import com.lrl.pojo.Member;
import com.lrl.pojo.Order;
import com.lrl.pojo.OrderSetting;
import com.lrl.service.OrderService;
import com.lrl.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/26 20:31
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * custom order service
     *
     * @param orderInfo
     */
    @Override
    @Transactional
    public void addorder(Map<String, String> orderInfo) throws HealthException{
        String orderDate = orderInfo.get("orderDate");
        String telephone = orderInfo.get("telephone");
        String name = orderInfo.get("name");
        String idCard = orderInfo.get("idCard");
        String sex = orderInfo.get("sex");
        String packageId = orderInfo.get("packageId");

        //there is plan in wanted day?
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDay(orderDate);
        if (null==orderSetting) {
            throw new HealthException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getReservations()>=orderSetting.getNumber()) {
            throw new HealthException(MessageConstant.ORDER_FULL);
        }
        if (null==telephone) {
            throw new HealthException(MessageConstant.ERROR_TELEPHONE_INPUT);
        }
        Member member = memberDao.findByTelephone(telephone);
        if (null==member) {
            // if not exist, add member
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setName(name);
            member.setIdCard(idCard);
            member.setSex(sex);
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        Integer memberId = member.getId();
        Order order = new Order();
        order.setMemberId(memberId);
        try {
            order.setOrderDate(DateUtils.parseString2Date(orderDate));
        } catch (Exception e) {
            //transactional 默认只捕获RuntimeException
            throw new HealthException(MessageConstant.ODER_FAILED_MAYBE_ERROR_DATE);
        }
        List<Order> orders = orderDao.findByCondition(order);
        if (null!=orders && orders.size()>0) {
            throw new HealthException(MessageConstant.HAS_ORDERED);
        }
        order.setPackageId(Integer.valueOf(packageId));
        order.setOrderType(orderInfo.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);
        orderSettingDao.addReservationByOrderdate(orderDate);
    }

    @Override
    public List<Map> findHotPackage() {
        return orderDao.findHotPackage();
    }

}