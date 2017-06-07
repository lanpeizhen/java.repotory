package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.HisInpatientInfo;
/**
 * 手术病人信息视图service
 * @author lanpz
 * @date 2016年8月3日
 *
 */
public interface HisInpatientInfoService {
    
    List<HisInpatientInfo> getOracleList(int begin, int end);
    
    List<HisInpatientInfo> getMysqlList(List<HisInpatientInfo> oracleList);
    
    int copyHisInpatient(List<HisInpatientInfo> oracleList, List<HisInpatientInfo> mysqlList);
}
