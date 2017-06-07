package com.aek.service.transfer.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.MHisDeptInfoMapper;
import com.aek.persistence.oracle.beans.HisDeptInfo;
import com.aek.persistence.oracle.mapper.OHisDeptInfoMapper;
import com.aek.service.transfer.HisDeptInfoService;

@Service("hisDeptInfoService")
@Transactional(rollbackFor = Exception.class)
public class HisDeptInfoServiceImpl implements HisDeptInfoService {
    @Autowired
    private OHisDeptInfoMapper oHisDeptInfoMapper;

    @Autowired
    private MHisDeptInfoMapper mHisDeptInfoMapper;

    @Override
    @DataSource (name = DataSource.oracle)
    public List<HisDeptInfo> getOracleList(int begin, int end) {
        return oHisDeptInfoMapper.getAll(begin, end);
    }

    @Override
    @DataSource (name = DataSource.mysql)
    public List<HisDeptInfo> getMysqlList(List<HisDeptInfo> deptOracleList) {
        return mHisDeptInfoMapper.getAll(deptOracleList);
    }

    
    @Override
    @DataSource (name = DataSource.mysql)
    public int copyDeptInfo(List<HisDeptInfo> oracleList, List<HisDeptInfo> mysqlList) {
        List<HisDeptInfo> updateList = new ArrayList<HisDeptInfo>();
        List<HisDeptInfo> addList = new ArrayList<HisDeptInfo>();
        if (oracleList != null && oracleList.size() > 0) {
            addList.addAll(oracleList);
            if (mysqlList != null && mysqlList.size() > 0) {
                Iterator<HisDeptInfo> iterator = addList.iterator();
                
                // 查出需要添加和修改的数据信息放到列表中
                while (iterator.hasNext()) {
                    HisDeptInfo oracle = iterator.next();
                    Iterator<HisDeptInfo> mysqlIt = mysqlList.iterator();
                    while(mysqlIt.hasNext()) {
                        HisDeptInfo mysql = mysqlIt.next();
                        if (oracle.getDeptNo().equals(mysql.getDeptNo())) {
                            if (!oracle.toString().equals(mysql.toString())) {
                                updateList.add(oracle);
                            }
                            iterator.remove();
                            mysqlIt.remove();
                        }
                    }
                }
            }
            int addModifyNum = 0;
            if (addList != null && addList.size() > 0) {
                addModifyNum = this.mHisDeptInfoMapper.addList(addList);
            }
            int updateModifyNum = 0;
            if (updateList != null && updateList.size() > 0) {
                updateModifyNum = this.mHisDeptInfoMapper.updateList(updateList);
            }
            if (updateModifyNum >= 0 && addModifyNum == addList.size()) {
                return 1;
            }
        }
        return 0;
    }

}
