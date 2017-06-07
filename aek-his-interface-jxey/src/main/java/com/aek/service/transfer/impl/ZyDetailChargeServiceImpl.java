package com.aek.service.transfer.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek.common.datasource.DataSource;
import com.aek.persistence.mysql.mapper.MZyDetailChargeMapper;
import com.aek.persistence.oracle.beans.ZyDetailCharge;
import com.aek.persistence.oracle.mapper.OZyDetailChargeMapper;
import com.aek.service.transfer.ZyDetailChargeService;

@Service("zyDetailChargeService")
@Transactional(rollbackFor = Exception.class)
public class ZyDetailChargeServiceImpl implements ZyDetailChargeService {

    @Autowired
    private OZyDetailChargeMapper oZyDetailChargeMapper;
    @Autowired
    private MZyDetailChargeMapper mZyDetailChargeMapper;

    @Override
    @DataSource(name = DataSource.oracle)
    public List<ZyDetailCharge> getOracleList(int begin, int end) {
        return oZyDetailChargeMapper.getZyDetailChargeList(begin, end);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public List<ZyDetailCharge> getMysqlList(List<ZyDetailCharge> detailOracleList) {
        return mZyDetailChargeMapper.getZyDetailChargeList(detailOracleList);
    }

    @Override
    @DataSource(name = DataSource.mysql)
    public int copyMZyDetailCharge(List<ZyDetailCharge> oracleList, List<ZyDetailCharge> mysqlList) {
        List<ZyDetailCharge> updateList = new ArrayList<ZyDetailCharge>();
        List<ZyDetailCharge> addList = new ArrayList<ZyDetailCharge>();
        addList.addAll(oracleList);
        if (oracleList != null && oracleList.size() > 0) {
            if (mysqlList != null && mysqlList.size() > 0) {
                Iterator<ZyDetailCharge> iterator = addList.iterator();

                // 查出需要添加和修改的数据信息放到列表中
                while (iterator.hasNext()) {
                    ZyDetailCharge oracle = iterator.next();
                    Iterator<ZyDetailCharge> mysqlIt = mysqlList.iterator();
                    while (mysqlIt.hasNext()) {
                        ZyDetailCharge mysql = mysqlIt.next();
                        if (oracle.getChargeId().intValue() == mysql.getChargeId().intValue()) {
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
            if (null != addList && addList.size() > 0) {
                addModifyNum = this.mZyDetailChargeMapper.addList(addList);
            }
            int updateModifyNum = 0;
            if (null != updateList && updateList.size() > 0) {
                updateModifyNum = this.mZyDetailChargeMapper.updateList(updateList);
            }
            if (updateModifyNum >= 0 && addModifyNum == addList.size()) {
                return 1;
            }
        }
        return 0;
    }

}
