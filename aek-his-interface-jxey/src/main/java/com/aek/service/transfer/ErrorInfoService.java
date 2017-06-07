package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.DataErrorInfo;

public interface ErrorInfoService {
    int addErrorInfo(DataErrorInfo dataErrorInfo);
    
    List<DataErrorInfo> getErrorList();

    /**
     * 获取待执行的错误记录
     * @param end 
     * @param begin 
     * @return
     */
    List<DataErrorInfo> getExcuteList(int begin, int end);

    /**
     * 修改错误记录信息的执行状态。
     * @param id
     */
    int update(Integer id);

    /**
     * 再次出错时对错误信息进行处理
     * @param mysqlId
     * @return
     */
    int updateError(Integer mysqlId);

    /**
     * 获取错误记录的总数
     * @return
     */
    int getErrorCount();
}
