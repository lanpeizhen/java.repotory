package com.aek.service.transfer.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.MHisInpatientInfoMapper;
import com.aek.persistence.oracle.beans.HisInpatientInfo;
import com.aek.persistence.oracle.mapper.OHisInpatientInfoMapper;
import com.aek.service.transfer.HisInpatientInfoService;

@Service("hisInpatientInfoService")
@Transactional(rollbackFor = Exception.class)
public class HisInpatientInfoServiceImpl implements HisInpatientInfoService {

    @Autowired
    private OHisInpatientInfoMapper ohisInpatientInfoMapper;

    @Autowired
    private MHisInpatientInfoMapper mHisInpatientInfoMapper;

    @Override
    @DataSource(name = DataSource.oracle)
    public List<HisInpatientInfo> getOracleList(int begin, int end) {
        return ohisInpatientInfoMapper.getAll(begin, end);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public List<HisInpatientInfo> getMysqlList(List<HisInpatientInfo> oracleList) {
        return mHisInpatientInfoMapper.getAll(oracleList);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int copyHisInpatient(List<HisInpatientInfo> oracleList, List<HisInpatientInfo> mysqlList) {
        List<HisInpatientInfo> updateList = new ArrayList<HisInpatientInfo>();
        List<HisInpatientInfo> addList = new ArrayList<HisInpatientInfo>();
        if (oracleList != null && oracleList.size() > 0) {
            addList.addAll(oracleList);
            if (mysqlList != null && mysqlList.size() > 0) {
                Iterator<HisInpatientInfo> iterator = addList.iterator();

                // 查出需要添加和修改的数据信息放到列表中
                while (iterator.hasNext()) {
                    HisInpatientInfo oracle = iterator.next();
                    Iterator<HisInpatientInfo> mysqlIt = mysqlList.iterator();
                    while (mysqlIt.hasNext()) {
                        HisInpatientInfo mysql = mysqlIt.next();
                        if (oracle.getOperationId().intValue() == mysql.getOperationId().intValue()
                                && oracle.getOperationId().intValue() != -1
                                && oracle.getInpatientNo().equals(mysql.getInpatientNo())
                                && oracle.getPatientNo().equals(mysql.getPatientNo())
                                && oracle.getOperationName().equals(mysql.getOperationName())
                                && oracle.getOperationDate().equals(mysql.getOperationDate())) {
                            if (!oracle.toString().equals(mysql.toString())) {
                                updateList.add(oracle);
                            }
                            mysqlIt.remove();
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
            int addModifyNum = 0;
            if (addList != null && addList.size() > 0) {
                addModifyNum = this.mHisInpatientInfoMapper.addList(addList);
            }
            int updateModifyNum = 0;
            if (updateList != null && updateList.size() > 0) {
                updateModifyNum = this.mHisInpatientInfoMapper.updateList(updateList);
            }
            if (updateModifyNum >= 0 && addModifyNum == addList.size()) {
                return 1;
            }
        }

        return 0;
    }

}
