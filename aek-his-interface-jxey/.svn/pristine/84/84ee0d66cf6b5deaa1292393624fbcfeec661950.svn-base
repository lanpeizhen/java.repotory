package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.HisUserInfo;
/**
 * HIS 人员信息视图Service
 * @author lanpz
 * @date 2016年8月3日
 *
 */
public interface HisUerInfoService {
    List<HisUserInfo> getOracleList(int begin, int end);
    
    List<HisUserInfo> getMysqlList(List<HisUserInfo> oracleList);
    
    int copyUser(List<HisUserInfo> oracleList, List<HisUserInfo> mysqlList);
}
