package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.BdHplGoodsSpecs;

/**
 * 物资规格。操作服务接口
 * 
 * @author Lanpz
 * @date 2016-12-15
 * @source Generated by MySQL2Java
 */
public interface HplGoodsSpecsService {
    /**
     * 获取mysql数据库中产品列表信息
     * @param begin
     * @param end
     * @return
     */
    List<BdHplGoodsSpecs> getMysqlList(Integer begin, Integer end, Integer dateFlag);

    /**
     * 获取oracle数据库中产品列表信息
     * @param mysqlList
     * @return
     */
    List<BdHplGoodsSpecs> getOracleList(List<BdHplGoodsSpecs> mysqlList);
    
    /**
     * 复制mysql的规格信息到oracle数据库中
     * @param mysqlList
     * @param oracleList
     * @return
     */
    int copyMysql2Oracle(List<BdHplGoodsSpecs> mysqlList, List<BdHplGoodsSpecs> oracleList);

    /**
     * 获取oracle数据库中物资规格列表的长度
     * @return
     */
    int getOracleList();
}
