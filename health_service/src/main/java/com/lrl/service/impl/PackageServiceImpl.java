package com.lrl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrl.dao.PackageDao;
import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.exception.HealthException;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.Package;
import com.lrl.service.PackageService;
import com.lrl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/22 20:53
 */
@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageDao packageDao;
    /**
     * 分页
     * @return
     * @param pageBean
     */
    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        if (pageBean.getQueryString()!=null && pageBean.getQueryString().length()>0) {
            pageBean.setQueryString("%"+pageBean.getQueryString()+"%");
        }
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        List<Package> packageList = packageDao.findPageByCondition(pageBean.getQueryString());
        PageInfo<Package> pageInfo = new PageInfo<Package>(packageList);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void add(Package pkg, Integer[] groupIds) {
        //todo:add package table
        packageDao.addPackage(pkg);
        Integer pkgId = pkg.getId();

        //todo: add relations at packageAndGroup table
        for (Integer groupId : groupIds) {
            packageDao.addRelationForPkgAndGroup(pkgId,groupId);
        }
    }

    /**
     * findPackageById
     *
     * @param packageId
     * @return
     */
    @Override
    public Package findPackageById(Integer packageId) {
        Package pkg = packageDao.findPackageAllInfoById(packageId);
        pkg.setImg(QiniuUtils.domain+pkg.getImg());
        return pkg;
    }

    /**
     * findGroupIdsByPackageId
     *  find from relation table
     * @param packageId
     * @return
     */
    @Override
    public Integer[] findGroupIdsByPackageId(Integer packageId) {
        return packageDao.findGroupIdsByPackageId(packageId);
    }

    /**
     * update
     *
     * @param pkg
     * @param groupIds
     */
    @Override
    public void update(Package pkg, Integer[] groupIds) {
        Integer pkgId = pkg.getId();
        packageDao.deleteRelationById(pkgId);
        for (Integer groupId : groupIds) {
            packageDao.addRelationForPkgAndGroup(pkgId,groupId);
        }
        packageDao.update(pkg);
    }

    /**
     * find all packages
     *
     * @return
     */
    @Override
    public List<Package> findAll() {
        List<Package> packageList = packageDao.findAll();
        String domain_url = QiniuUtils.domain;
        packageList.forEach(pkg->{pkg.setImg(domain_url+pkg.getImg());});
        return packageList;
    }

    @Override
    public Package findPackageAllInfoById(Integer packageId) {
        return packageDao.findPackageAllInfoById(packageId);
    }

    @Override
    public CheckGroup getGroupByPkgId(Integer packageId) {
        return packageDao.getGroupByPkgId(packageId);
    }

    /**
     * 获取package简单信息,不包括groups
     *
     * @param packageId
     * @return
     */
    @Override
    public Package findSimplePackageInfo(Integer packageId) {
        Package pkg = packageDao.findSimplePackageInfo(packageId);
        pkg.setImg(QiniuUtils.domain+pkg.getImg());
        return pkg;
    }

    /**
     * get packageReport
     *
     * @return map include keys[packageNames,packageCount]
     */
    @Override
    public Map<String, Object> getPackageReport() {
        List<Map<String, String>> packageCount = packageDao.getPackageCountAndName();
        if (packageCount!=null) {
            List<String> packageNames = new ArrayList<>();
            packageCount.forEach(map->{
                packageNames.add(map.get("name"));
            });
            Map<String,Object> map = new ConcurrentHashMap<>();
            map.put("packageNames",packageNames);
            map.put("packageCount",packageCount);
            return map;
        }
        return null;
    }
}