package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.DataErrorInfo;
import com.aek.persistence.oracle.beans.ZyChargeInterface;

public interface ZyChargeInfoService {
    List<ZyChargeInterface> getOracleList(List<ZyChargeInterface> mysqlList);
    
    /**
     * 排除掉错误信息之后的接口信息
     * 
     * @param allErrorList
     * @return
     */
    List<ZyChargeInterface> getMysqlList(int begin, int end);

    /**
     * 病人收费接口
     * @param mysql
     * @param oracleList
     * @return
     * @throws Exception
     */
    int copyZyChargeInfo(ZyChargeInterface mysql, List<ZyChargeInterface> oracleList) throws Exception;
    

    /**
     * 查找待完成的错误信息（执行次数小于3）
     * 
     * @param errorList
     * @return
     */
    List<ZyChargeInterface> getErrorCharge(List<DataErrorInfo> errorList);

    /**
     * 单独处理错误信息
     * @param zyChargeInfo
     * @return
     * @throws Exception
     */
    int insert(ZyChargeInterface zyChargeInfo) throws Exception;

}
