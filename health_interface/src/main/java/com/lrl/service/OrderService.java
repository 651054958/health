package com.lrl.service;

import com.lrl.exception.HealthException;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     * custom order service
     * @param orderInfo
     */
    void addorder(Map<String, String> orderInfo) throws HealthException;


    List<Map> findHotPackage();

}
