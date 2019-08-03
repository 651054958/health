package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/8/3 16:27
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;
}