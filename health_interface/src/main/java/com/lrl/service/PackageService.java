package com.lrl.service;

import com.lrl.entity.PageResult;
import com.lrl.entity.QueryPageBean;
import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.Package;

import java.util.List;
import java.util.Map;

public interface PackageService {
    /**
     * 分页
     * @return
     * @param pageBean
     */
    PageResult findPage(QueryPageBean pageBean);

    /**
     * add package
     * @param pkg
     * @param groupIds
     */
    void add(Package pkg, Integer[] groupIds);

    /**
     * findPackageById detail
     * @param packageId
     * @return
     */
    Package findPackageById(Integer packageId);

    /**
     * findGroupIdsByPackageId
     * @param packageId
     * @return
     */
    Integer[] findGroupIdsByPackageId(Integer packageId);

    /**
     * update
     * @param pkg
     * @param groupIds
     */
    void update(Package pkg, Integer[] groupIds);

    /**
     * find all packages
     * @return
     */
    List<Package> findAll();

    /**
     * find package detail
     * @param packageId
     * @return
     */
    Package findPackageAllInfoById(Integer packageId);

    /**
     * may be in groupService?
     * @param packageId
     * @return
     */
    CheckGroup getGroupByPkgId(Integer packageId);

    /**
     * 获取package简单信息,不包括groups
     * @param packageId
     * @return
     */
    Package findSimplePackageInfo(Integer packageId);

    /**
     * get packageReport
     * @return map include keys[packageNames,packageCount]
     */
    Map<String,Object> getPackageReport();


}
