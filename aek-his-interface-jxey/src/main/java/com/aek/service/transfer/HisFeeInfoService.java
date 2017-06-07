package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.HisFeeInfo;

public interface HisFeeInfoService {
    List<HisFeeInfo> getOracleList(int begin, int end);
    
    List<HisFeeInfo> getMysqlList(List<HisFeeInfo> feeOracleList);
    
    int copyFeeInfo(List<HisFeeInfo> oracleList, List<HisFeeInfo> mysqlList);
}
