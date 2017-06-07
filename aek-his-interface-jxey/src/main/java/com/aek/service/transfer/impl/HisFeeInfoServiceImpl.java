package com.aek.service.transfer.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.MHisFeeInfoMapper;
import com.aek.persistence.oracle.beans.HisFeeInfo;
import com.aek.persistence.oracle.mapper.OHisFeeInfoMapper;
import com.aek.service.transfer.HisFeeInfoService;

@Service("hisFeeInfoService")
@Transactional(rollbackFor = Exception.class)
public class HisFeeInfoServiceImpl implements HisFeeInfoService {

    @Autowired
    private OHisFeeInfoMapper oHisFeeInfoMapper;

    @Autowired
    private MHisFeeInfoMapper mHisFeeInfoMapper;

    @Override
    @DataSource(name = DataSource.oracle)
    public List<HisFeeInfo> getOracleList(int begin, int end) {
        return oHisFeeInfoMapper.getAll(begin, end);
    }
    
    @Override
    @DataSource(name = DataSource.mysql)
    public List<HisFeeInfo> getMysqlList(List<HisFeeInfo> feeOracleList) {
        return mHisFeeInfoMapper.getAll(feeOracleList);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int copyFeeInfo(List<HisFeeInfo> oracleList, List<HisFeeInfo> mysqlList) {
        List<HisFeeInfo> updateList = new ArrayList<HisFeeInfo>();
        List<HisFeeInfo> addList = new ArrayList<HisFeeInfo>();
        addList.addAll(oracleList);
        if (oracleList != null && oracleList.size() > 0) {
            if (mysqlList != null && mysqlList.size() > 0) {
                Iterator<HisFeeInfo> iterator = addList.iterator();
                
                // 查出需要添加和修改的数据信息放到列表中
                while (iterator.hasNext()) {
                    HisFeeInfo oracle = iterator.next();
                    Iterator<HisFeeInfo> mysqlIt = mysqlList.iterator();
                    while(mysqlIt.hasNext()) {
                        HisFeeInfo mysql = mysqlIt.next();
                        if (oracle.getFeeNo().equals(mysql.getFeeNo())) {
                            if (!oracle.toString().equals(mysql.toString())) {
                                updateList.add(oracle);
                            }
                            iterator.remove();
                            mysqlIt.remove();
                            break;
                        }
                    }
                }
            }
            int addModifyNum = 0;
            if (addList != null && addList.size() > 0) {
                    addModifyNum = this.mHisFeeInfoMapper.addList(addList);
            }
            int updateModifyNum = 0;
            if (updateList != null && updateList.size() > 0) {
                updateModifyNum = this.mHisFeeInfoMapper.updateList(updateList);
            }
            if (updateModifyNum >= 0 && addModifyNum == addList.size()) {
                return 1;
            }
        }

        return 0;
    }

}
