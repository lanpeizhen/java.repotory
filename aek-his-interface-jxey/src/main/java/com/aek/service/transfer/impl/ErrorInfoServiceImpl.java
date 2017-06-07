package com.aek.service.transfer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.DataErrorInfoMapper;
import com.aek.persistence.oracle.beans.DataErrorInfo;
import com.aek.service.transfer.ErrorInfoService;

@Service("errorInfoService")
@Transactional(rollbackFor = Exception.class)
public class ErrorInfoServiceImpl implements ErrorInfoService {

    @Autowired
    private DataErrorInfoMapper dataErrorInfoMapper;

    @Override
    @DataSource(name = DataSource.mysql)
    public int addErrorInfo(DataErrorInfo dataErrorInfo) {
        int rs = 0;
        rs = dataErrorInfoMapper.insert(dataErrorInfo);
        return rs;
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public List<DataErrorInfo> getErrorList() {
        List<DataErrorInfo> errorList = this.dataErrorInfoMapper.getErrorList();
        return errorList;
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public List<DataErrorInfo> getExcuteList(int begin, int end) {
        List<DataErrorInfo> errorList = this.dataErrorInfoMapper.getExcuteList(begin, end);
        return errorList;
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int update(Integer id) {
        int rs = this.dataErrorInfoMapper.updateByKey(id);
        return rs;
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int updateError(Integer mysqlId) {
        int rs = this.dataErrorInfoMapper.updateError(mysqlId);
        return rs;
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int getErrorCount() {
        return this.dataErrorInfoMapper.getErrorCount();
    }

}
