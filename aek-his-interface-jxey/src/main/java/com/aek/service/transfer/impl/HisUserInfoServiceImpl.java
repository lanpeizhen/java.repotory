package com.aek.service.transfer.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.MHisUserInfoMapper;
import com.aek.persistence.oracle.beans.HisUserInfo;
import com.aek.persistence.oracle.mapper.OHisUserInfoMapper;
import com.aek.service.transfer.HisUerInfoService;

@Service("hisUserInfoService")
@Transactional(rollbackFor = Exception.class)
public class HisUserInfoServiceImpl implements HisUerInfoService {

    @Autowired
    private MHisUserInfoMapper mHisUserInfoMapper;

    @Autowired
    private OHisUserInfoMapper oHisUserInfoMapper;

    @Override
    @DataSource(name = DataSource.oracle)
    public List<HisUserInfo> getOracleList(int begin, int end) {
        return oHisUserInfoMapper.getAll(begin, end);
    }
    

    @Override
    @DataSource(name = DataSource.mysql)
    public List<HisUserInfo> getMysqlList(List<HisUserInfo> oracleList) {
        return mHisUserInfoMapper.getAll(oracleList);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int copyUser(List<HisUserInfo> oracleList, List<HisUserInfo> mysqlList) {
        List<HisUserInfo> updateList = new ArrayList<HisUserInfo>();
        List<HisUserInfo> addList = new ArrayList<HisUserInfo>();
        if (oracleList != null && oracleList.size() > 0) {
            addList.addAll(oracleList);
            if (mysqlList != null && mysqlList.size() > 0) {
                Iterator<HisUserInfo> iterator = addList.iterator();
                
                // 查出需要添加和修改的数据信息放到列表中
                while (iterator.hasNext()) {
                    HisUserInfo oracle = iterator.next();
                    Iterator<HisUserInfo> mysqlIt = mysqlList.iterator();
                    while(mysqlIt.hasNext()) {
                        HisUserInfo mysql = mysqlIt.next();
                        if (oracle.getUserNo().equals(mysql.getUserNo())) {
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
                    addModifyNum += this.mHisUserInfoMapper.addList(addList);
            }
            int updateModifyNum = 0;
            if (updateList != null && updateList.size() > 0) {
                updateModifyNum += this.mHisUserInfoMapper.updateList(updateList);
            }
            if (updateModifyNum >= 0 && addModifyNum == addList.size()) {
                return 1;
            }
        }
        return 0;
    }

}
