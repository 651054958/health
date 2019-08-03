package com.lrl.dao;

import com.lrl.pojo.CheckGroup;
import com.lrl.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PackageDao {

    /**
     * findPageByCondition
     * @param queryString
     * @return
     */
    List<Package> findPageByCondition(String queryString);

    /**
     * add pkg
     * @param pkg
     */
    void addPackage(Package pkg);

    /**
     * add relation for pkgAndGroup table
     * @param pkgId
     * @param groupId
     */
    void addRelationForPkgAndGroup(@Param("pkgId") Integer pkgId, @Param("groupId") Integer groupId);

    /**
     * findPackageById
     * @param packageId
     * @return
     */
    Package findPackageById(Integer packageId);

    /**
     * findGroupIdsByPackageId
     * from relation table
     * @return
     * @param packageId
     */
    Integer[] findGroupIdsByPackageId(Integer packageId);

    /**
     * delete by id
     * @param pkgId
     */
    void deleteRelationById(Integer pkgId);

    /**
     * update
     * @param pkg
     */
    void update(Package pkg);

    /**
     * find all packages
     * @return
     */
    List<Package> findAll();

    /**
     * get package detail
     * @param packageId
     * @return
     */
    Package findPackageAllInfoById(Integer packageId);

    /**
     * getGroupsByPkgId not include groupList obj
     * @param packageId
     * @return
     */
    CheckGroup getGroupByPkgId(Integer packageId);

    /**
     * find simple package info
     * @param packageId
     * @return
     */
    Package findSimplePackageInfo(Integer packageId);

    /**
     * getPackageCountAndName
     * @return
     */
    List<Map<String, String>> getPackageCountAndName();

    /**
     * findHotPackage
     * @return
     */
    List<Map<String,String>> findHotPackage();

}
