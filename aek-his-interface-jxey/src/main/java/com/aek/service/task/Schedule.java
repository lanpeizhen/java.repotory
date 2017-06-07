package com.aek.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.aek.persistence.oracle.beans.BdHplGoodsSpecs;
import com.aek.persistence.oracle.beans.BdHplGoodses;
import com.aek.persistence.oracle.beans.DataErrorInfo;
import com.aek.persistence.oracle.beans.HisDeptInfo;
import com.aek.persistence.oracle.beans.HisFeeInfo;
import com.aek.persistence.oracle.beans.HisInpatientInfo;
import com.aek.persistence.oracle.beans.HisUserInfo;
import com.aek.persistence.oracle.beans.SdHplStockOut;
import com.aek.persistence.oracle.beans.SdHplStockOutItems;
import com.aek.persistence.oracle.beans.ZyChargeInterface;
import com.aek.persistence.oracle.beans.ZyDetailCharge;
import com.aek.service.transfer.ErrorInfoService;
import com.aek.service.transfer.HisDeptInfoService;
import com.aek.service.transfer.HisFeeInfoService;
import com.aek.service.transfer.HisInpatientInfoService;
import com.aek.service.transfer.HisUerInfoService;
import com.aek.service.transfer.HplGoodsSpecsService;
import com.aek.service.transfer.HplGoodsesService;
import com.aek.service.transfer.SdHplStockOutItemsService;
import com.aek.service.transfer.SdHplStockOutService;
import com.aek.service.transfer.ZyChargeInfoService;
import com.aek.service.transfer.ZyDetailChargeService;
import com.mb.common.util.DateUtils;
import com.mb.common.util.ObjectUtils;

public class Schedule {

    @Autowired
    private HisUerInfoService hisUserInfoService;

    @Autowired
    private HisFeeInfoService hisFeeInfoService;

    @Autowired
    private HisInpatientInfoService hisInpatientInfoService;

    @Autowired
    private HisDeptInfoService hisDeptInfoService;

    @Autowired
    private ZyDetailChargeService zyDetailChargeService;

    @Autowired
    private ErrorInfoService errorInfoService;

    @Autowired
    private ZyChargeInfoService zyChargeInfoService;

    @Autowired
    private HplGoodsesService hplGoodsesService;

    @Autowired
    private HplGoodsSpecsService hplGoodsSpecService;

    @Autowired
    private SdHplStockOutService sdHplStockOutService;

    @Autowired
    private SdHplStockOutItemsService sdHplStockOutItemsService;

    private static Logger log = Logger.getLogger(Schedule.class);

    /**
     * 服务启动时执行
     * 
     * @Description
     * @author sunhanbin
     * @date 2015-4-18下午09:01:27
     */

    public void runBySeverStart() {
        log.info("--------------Start：runByServerStart()---------------");
        int size = 500;

        copyStockOut(size);

        copyStockOutItems(size);
        log.info("--------------End  ：runByServerStart()---------------");
    }

    /**
     * 每小时执行一次
     * 
     * @Description
     * @author sunhanbin
     * @date 2015-4-18下午09:01:38
     */
    public void runByHour() {
        log.info("--------------Start：runByHour()-----------------");
        log.info("--------------End  ：runByHour()-----------------");
    }

    /**
     * 每十分钟执行一次
     * 
     * @Description
     * @author sunhanbin
     * @date 2015-4-18下午09:04:11
     */

    public void runByTenMinute() {
        log.info("--------------Start：runByTwoMinute()---------------");
        // 连接到oracle的数据源信息
        int size = 500;

        /*copyDept(size);

        copyFee(size);

        copyInpatient(size);

        copyUser(size);

        copyDetail(size);

        copyGoods(size);

        copyGoodsSpec(size);
*/
      //  copyStockOut(size);

        copyStockOutItems(size);

        // 将oracle中的数据与mysql中的数据进行处理
        int chargeRs = this.copyChargeInfo(size);

        log.info("--------------End  ：runByTwoMinute()---------------");
    }

    /**
     * 同步收费耗材明细信息
     * 
     * @param size
     */
    private void copyDetail(int size) {
        try {
            int i = 1;
            while (i != 0) {
                List<ZyDetailCharge> detailOracleList = null;
                detailOracleList = zyDetailChargeService.getOracleList((i - 1) * size, i * size + 1);
                if (ObjectUtils.isNotEmpty(detailOracleList)) {
                    List<ZyDetailCharge> detailMysqlList = this.zyDetailChargeService.getMysqlList(detailOracleList);
                    this.zyDetailChargeService.copyMZyDetailCharge(detailOracleList, detailMysqlList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy ZyDetailCharge occured a problem , the message info is : ", e);
        }
    }

    /**
     * 同步医院用户信息
     * 
     * @param size
     */
    private void copyUser(int size) {
        int userRs = 0;
        try {
            int i = 1;
            while (i != 0) {
                List<HisUserInfo> userOracleList = null;
                userOracleList = this.hisUserInfoService.getOracleList((i - 1) * size, i * size + 1);
                if (ObjectUtils.isNotEmpty(userOracleList)) {
                    List<HisUserInfo> userMysqlList = this.hisUserInfoService.getMysqlList(userOracleList);
                    userRs += this.hisUserInfoService.copyUser(userOracleList, userMysqlList);
                    i++;
                } else {
                    i = 0;
                }
            }
            userRs = userRs == i ? 1 : 0;
        } catch (Exception e) {
            log.error("copy HisUserInfo occured a problem , the message info is :", e);
        }
    }

    /**
     * 同步病人信息
     * 
     * @param size
     */
    private void copyInpatient(int size) {
        try {
            int i = 1;
            while (i != 0) {
                List<HisInpatientInfo> inpatientOracleList = null;
                Integer begin = (i - 1) * size;
                Integer end = i * size + 1;
                inpatientOracleList = this.hisInpatientInfoService.getOracleList(begin, end);
                if (ObjectUtils.isNotEmpty(inpatientOracleList)) {
                    List<HisInpatientInfo> inpaitentMysqlList = this.hisInpatientInfoService
                            .getMysqlList(inpatientOracleList);
                    this.hisInpatientInfoService.copyHisInpatient(inpatientOracleList, inpaitentMysqlList);
                    i++;
                } else {
                    i = 0;
                }
            }

        } catch (Exception e) {
            log.error("copy HisInpatientInfo occured a problem , the message info is : ", e);
        }
    }

    /**
     * 同步收费代码信息
     * 
     * @param size
     */
    private void copyFee(int size) {
        try {
            int i = 1;
            while (i != 0) {
                List<HisFeeInfo> feeOracleList = null;
                feeOracleList = this.hisFeeInfoService.getOracleList((i - 1) * size, i * size + 1);
                if (ObjectUtils.isNotEmpty(feeOracleList)) {
                    List<HisFeeInfo> feeMysqlList = this.hisFeeInfoService.getMysqlList(feeOracleList);
                    this.hisFeeInfoService.copyFeeInfo(feeOracleList, feeMysqlList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy HisFeeInfo occured a problem , the message info is :", e);
        }
    }

    private void copyDept(int size) {
        try {
            int i = 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                Integer end = i * size + 1;
                List<HisDeptInfo> deptOracleList = null;
                deptOracleList = this.hisDeptInfoService.getOracleList(begin, end);
                if (ObjectUtils.isNotEmpty(deptOracleList)) {
                    List<HisDeptInfo> deptMysqlList = this.hisDeptInfoService.getMysqlList(deptOracleList);
                    this.hisDeptInfoService.copyDeptInfo(deptOracleList, deptMysqlList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy DeptInfo had occured a problem : ", e);
        }
    }

    /**
     * 同步物资信息
     * 
     * @param size
     */
    private void copyGoods(int size) {
        try {
            int i = 1;
            int oracleCount = this.hplGoodsesService.getOracleCount();
            Integer dateFlag = (oracleCount == 0) ? 0 : 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                List<BdHplGoodses> goodsMysqlList = this.hplGoodsesService.getMysqlList(begin, size, dateFlag);
                if (ObjectUtils.isNotEmpty(goodsMysqlList)) {
                    List<BdHplGoodses> goodsOracleList = null;
                    if (dateFlag == 1) {
                        goodsOracleList = this.hplGoodsesService.getOracleList(goodsMysqlList);
                    }
                    this.hplGoodsesService.copyMysql2Oracle(goodsMysqlList, goodsOracleList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy GoodInfo occured a problem , the message info is :", e);
        }
    }

    /**
     * 同步物资规格信息
     * 
     * @param size
     */
    private void copyGoodsSpec(int size) {
        try {
            int i = 1;
            int oracleCount = this.hplGoodsSpecService.getOracleList();
            int dateFlag = (oracleCount == 0) ? 0 : 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                List<BdHplGoodsSpecs> mysqlList = this.hplGoodsSpecService.getMysqlList(begin, size, dateFlag);
                if (ObjectUtils.isNotEmpty(mysqlList)) {
                    List<BdHplGoodsSpecs> oracleList = null;
                    if (dateFlag == 1) {
                        oracleList = this.hplGoodsSpecService.getOracleList(mysqlList);
                    }
                    this.hplGoodsSpecService.copyMysql2Oracle(mysqlList, oracleList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy GoodsSpec occured a problem , the message info is :", e);
        }
    }

    private void copyStockOut(int size) {
        try {
            int i = 1;
            int oracleCount = this.sdHplStockOutService.getOracleCount();
            int dateFlag = (oracleCount == 0) ? 0 : 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                List<SdHplStockOut> mysqlList = this.sdHplStockOutService.getMysqlList(begin, size, dateFlag);
                if (ObjectUtils.isNotEmpty(mysqlList)) {
                    List<SdHplStockOut> oracleList = null;
                    if (dateFlag == 1) {
                        oracleList = this.sdHplStockOutService.getOracleList(mysqlList);
                    }
                    this.sdHplStockOutService.copyMySql2Oracle(mysqlList, oracleList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy StockOut occured a problem , the message info is :", e);
        }
    }

    private void copyStockOutItems(int size) {
        try {
            int i = 1;
            int oracleCount = this.sdHplStockOutItemsService.getOracleCount();
            int dateFlag = (oracleCount == 0) ? 0 : 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                List<SdHplStockOutItems> mysqlList = this.sdHplStockOutItemsService.getMysqlList(begin, size, dateFlag);
                if (ObjectUtils.isNotEmpty(mysqlList)) {
                    List<SdHplStockOutItems> oracleList = null;
                    if (dateFlag == 1) {
                        oracleList = this.sdHplStockOutItemsService.getOracle(mysqlList);
                    }
                    this.sdHplStockOutItemsService.copyMysql2Oracle(mysqlList, oracleList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy StockOutItems occured a problem , the message info is :", e);
        }
    }

    /**
     * 每晚凌晨00：30分执行一次
     * 
     * @Description
     * @author sunhanbin
     * @date 2015-4-18下午09:01:57
     */

    public void runByMidnight() {
        log.info("--------------Start：runByMidnight()---------------");
        log.info("--------------End  ：runByMidnight()---------------");
    }

    public int copyChargeInfo(int size) {
        int rs = 0;
        Integer mysqlId = null;

        int i = 1;
        while (i != 0) {
            // 查询错误列表信息(执行次数小于三次)
            List<DataErrorInfo> errorList = this.errorInfoService.getExcuteList((i - 1) * size, size);
            if (ObjectUtils.isNotEmpty(errorList)) {
                // 查询到没有操作成功的接口列表信息
                List<ZyChargeInterface> excuteList = this.zyChargeInfoService.getErrorCharge(errorList);
                // 执行错误信息相关的数据en
                for (ZyChargeInterface zyChargeInfo : excuteList) {
                    mysqlId = zyChargeInfo.getId();
                    // 对重新对错误信息处理时进行try catch操作，如果出错则再次修改出错信息
                    try {
                        this.zyChargeInfoService.insert(zyChargeInfo);
                        this.errorInfoService.update(mysqlId);
                    } catch (Exception e1) {
                        this.errorInfoService.updateError(mysqlId);
                    }
                }
            } else {
                i = 0;
            }
        }
        try {
            int j = 1;
            while (j != 0) {
                List<ZyChargeInterface> mysqlList = this.zyChargeInfoService.getMysqlList((j - 1) * size, size);
                j += 1;
                if (ObjectUtils.isNotEmpty(mysqlList)) {
                    List<ZyChargeInterface> oracleList = this.zyChargeInfoService.getOracleList(mysqlList);
                    for (ZyChargeInterface mysql : mysqlList) {
                        try {
                            mysqlId = mysql.getId();
                            rs += this.zyChargeInfoService.copyZyChargeInfo(mysql, oracleList);
                        } catch (Exception e) {
                            DataErrorInfo errorInfo = new DataErrorInfo();
                            errorInfo.setErrorMsg(e.getMessage());
                            errorInfo.setPrimaryKey(mysqlId);
                            errorInfo.setExcuteTime(1);
                            errorInfo.setHappenTime(new DateUtils().format(new Date()));
                            errorInfo.setTableName("his_charge_interface");
                            errorInfo.setIsSolved(0);
                            this.errorInfoService.addErrorInfo(errorInfo);
                        }
                    }
                } else {
                    j = 0;
                }
            }
        } catch (Exception e) {
            DataErrorInfo errorInfo = new DataErrorInfo();
            errorInfo.setErrorMsg(e.getMessage());
            errorInfo.setExcuteTime(1);
            errorInfo.setHappenTime(new DateUtils().format(new Date()));
            errorInfo.setTableName("his_charge_interface");
            errorInfo.setIsSolved(0);
            this.errorInfoService.addErrorInfo(errorInfo);
        }
        return rs;
    }
}
