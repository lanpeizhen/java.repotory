package com.aek.service.transfer;

import java.util.List;

import com.aek.persistence.oracle.beans.ZyDetailCharge;

public interface ZyDetailChargeService {
    List<ZyDetailCharge> getOracleList(int begin, int end);

    List<ZyDetailCharge> getMysqlList(List<ZyDetailCharge> detailOracleList);

    int copyMZyDetailCharge(List<ZyDetailCharge> oracleList, List<ZyDetailCharge> mysqlList);
}
