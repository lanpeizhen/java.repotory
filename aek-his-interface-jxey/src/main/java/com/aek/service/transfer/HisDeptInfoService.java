package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.HisDeptInfo;


public interface HisDeptInfoService {
    List<HisDeptInfo> getOracleList(int begin, int end);
    
    /**
     * 获取sqlserver数据库中的科室信息
     * @param begin
     * @param end
     * @return
     */
    List<HisDeptInfo> getMysqlList(List<HisDeptInfo> deptOracleList);
    
    int copyDeptInfo(List<HisDeptInfo> oracleList, List<HisDeptInfo> mysqlList);
}
