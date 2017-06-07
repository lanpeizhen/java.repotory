package com.aek.service.transfer.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.MZyChargeInterfaceMapper;
import com.aek.persistence.oracle.beans.DataErrorInfo;
import com.aek.persistence.oracle.beans.ZyChargeInterface;
import com.aek.persistence.oracle.mapper.OZyChargeInterfaceMapper;
import com.aek.service.transfer.ZyChargeInfoService;
import com.mb.common.util.ObjectUtils;

@Service("zyChargeInfoService")
@Transactional(rollbackFor = Exception.class)
public class ZyChargeInfoServiceImpl implements ZyChargeInfoService {

    @Autowired
    private MZyChargeInterfaceMapper mZyChargeInfoMapper;

    @Autowired
    private OZyChargeInterfaceMapper oZyChargeInfoMapper;

    @Override
    @DataSource(name = DataSource.oracle)
    public List<ZyChargeInterface> getOracleList(List<ZyChargeInterface> mysqlList) {
        return oZyChargeInfoMapper.getList(mysqlList);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public List<ZyChargeInterface> getMysqlList(int begin, int end) {
        return mZyChargeInfoMapper.getList(begin, end);
    }

    
    
    @Override
    @DataSource(name = DataSource.oracle)
    public int copyZyChargeInfo(ZyChargeInterface mysql, List<ZyChargeInterface> oracleList) throws Exception {
        int rs = 0;
        if (null != oracleList && oracleList.size() > 0) {
            for (ZyChargeInterface oracle : oracleList) {
                if (oracle.getId().intValue() == mysql.getId().intValue()) {
                    mysql = null;
                    rs = 1;
                    return rs;
                }
            }
        }
        if (ObjectUtils.isNotEmpty(mysql)) {
            rs = this.oZyChargeInfoMapper.insert(mysql);
        }
        return rs;
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public List<ZyChargeInterface> getErrorCharge(List<DataErrorInfo> errorList) {
        List<ZyChargeInterface> excuteList = this.mZyChargeInfoMapper.getListByIds(errorList);
        return excuteList;
    }

    @Override
    @DataSource(name = DataSource.oracle)
    public int insert(ZyChargeInterface zyChargeInfo) throws Exception {
        int rs = this.oZyChargeInfoMapper.insert(zyChargeInfo);
        return rs;
    }

}
