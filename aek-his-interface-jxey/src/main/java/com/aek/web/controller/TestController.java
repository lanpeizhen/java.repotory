package com.aek.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aek.persistence.oracle.beans.BdHplGoodsSpecs;
import com.aek.persistence.oracle.beans.BdHplGoodses;
import com.aek.persistence.oracle.beans.HisFeeInfo;
import com.aek.persistence.oracle.beans.HisInpatientInfo;
import com.aek.persistence.oracle.beans.SdHplStockOut;
import com.aek.persistence.oracle.beans.SdHplStockOutItems;
import com.aek.service.task.Schedule;
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
import com.mb.common.util.ObjectUtils;

@Controller
@RequestMapping("/lpz")
public class TestController extends BaseController {

    private static Logger log = Logger.getLogger(TestController.class);

    @Autowired
    private SdHplStockOutService sdHplStockOutService;

    @Autowired
    private SdHplStockOutItemsService sdHplStockOutItemsService;

    @Autowired
    private HisUerInfoService hisUserInfoService;

    @Autowired
    private HisFeeInfoService hisFeeInfoService;

    @Autowired
    private HisInpatientInfoService hisInpatientInfoService;

    @Autowired
    private ZyDetailChargeService zyDetailChargeService;

    @Autowired
    private HisDeptInfoService hisDeptInfoService;

    @Autowired
    private ZyChargeInfoService zyChargeInfoService;

    @Autowired
    private ErrorInfoService errorInfoService;

    @Autowired
    private HplGoodsSpecsService hplGoodsSpecsService;

    @Autowired
    private HplGoodsesService hplGoodsesService;

    @ResponseBody
    @RequestMapping(value = "/patient")
    public String testPatient() {
        int size = 10;
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
        return "inpatient";
    }

    @ResponseBody
    @RequestMapping(value = "/testSpec")
    public String testSpec() {
        int size = 100;
        try {
            int i = 1;
            int oracleCount = this.hplGoodsSpecsService.getOracleList();
            int dateFlag = (oracleCount == 0) ? 0 : 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                List<BdHplGoodsSpecs> mysqlList = this.hplGoodsSpecsService.getMysqlList(begin, size, dateFlag);
                if (ObjectUtils.isNotEmpty(mysqlList)) {
                    List<BdHplGoodsSpecs> oracleList = null;
                    if (dateFlag == 1) {
                        oracleList = this.hplGoodsSpecsService.getOracleList(mysqlList);
                    }
                    this.hplGoodsSpecsService.copyMysql2Oracle(mysqlList, oracleList);
                    i++;
                } else {
                    i = 0;
                }
            }
        } catch (Exception e) {
            log.error("copy GoodsSpec occured a problem , the message info is :", e);
        }
        return "testSpec";
    }

    @ResponseBody
    @RequestMapping(value = "/testGoods")
    public String testGoods() {
        int size = 10;
        try {
            int i = 1;
            int oracleCount = this.hplGoodsesService.getOracleCount();
            Integer dateFlag = (oracleCount == 0) ? 0 : 1;
            while (i != 0) {
                Integer begin = (i - 1) * size;
                List<BdHplGoodses> goodsMysqlList = hplGoodsesService.getMysqlList(begin, size, dateFlag);
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
        return "testSpec";
    }

    @ResponseBody
    @RequestMapping(value = "/testStockOut")
    public String testStockOut() {
        int size = 500;
        try {
            int i = 1;
            int oracleCount = sdHplStockOutService.getOracleCount();
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
        return "stockOut";
    }

    @ResponseBody
    @RequestMapping(value = "/testStockOutItems")
    public String testStockOutItems() {
        int size = 100;
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
        return "outItmes";
    }

    // /**
    // * 查询两个数据源中的数据
    // */
    // @ResponseBody
    // @RequestMapping("/all")
    // public void getList() {
    // List<HisDeptInfo> oracleList = this.hisDeptInfoService.getOracleList();
    // List<HisDeptInfo> mysqlTableList = hisDeptInfoService.getMysqlList();
    // System.out.println("oracleList:" + oracleList.size());
    // System.out.println("mySqlList:" + mysqlTableList.size());
    // }
    //
    // //
    // /**
    // * 拷贝两个数据库中的数据
    // *
    // * @return
    // */
    // @ResponseBody
    // @RequestMapping("/copyDept")
    // public int copyDept() {
    // List<HisDeptInfo> oracleList = this.hisDeptInfoService.getOracleList();
    // List<HisDeptInfo> mysqlList = hisDeptInfoService.getMysqlList();
    // int rs = this.hisDeptInfoService.copyDeptInfo(oracleList, mysqlList);
    // return rs;
    // }
    //
    // @ResponseBody
    // @RequestMapping("/copyUser")
    // public int copyUser() {
    // List<HisUserInfo> oracleList = this.hisUserInfoService.getOracleList();
    // List<HisUserInfo> mysqlList = this.hisUserInfoService.getMysqlList();
    // int rs = this.hisUserInfoService.copyUser(oracleList, mysqlList);
    // return rs;
    // }
    //
    @ResponseBody
    @RequestMapping("/copyFee")
    public int copyFee() {
        int size = 1000;
        int userRs = 0;
        try {
            int i = 1;
            while (i != 0) {
                List<HisFeeInfo> userOracleList = null;
                userOracleList = this.hisFeeInfoService.getOracleList((i - 1) * size + 1, i * size + 1);
                i += 1;
                if (ObjectUtils.isNotEmpty(userOracleList)) {
                    List<HisFeeInfo> userMysqlList = this.hisFeeInfoService.getMysqlList(userOracleList);
                    userRs += this.hisFeeInfoService.copyFeeInfo(userOracleList, userMysqlList);
                } else {
                    i = 0;
                }
            }
            userRs = userRs == i ? 1 : 0;
        } catch (Exception e) {
        }
        return userRs;
    }
    //
    // @ResponseBody
    // @RequestMapping("/copyInpatient")
    // public int copyInpatient() {
    // List<HisInpatientInfo> oracleList =
    // this.hisInpatientInfoService.getOracleList();
    // List<HisInpatientInfo> mysqlList =
    // this.hisInpatientInfoService.getMysqlList();
    // int rs = this.hisInpatientInfoService.copyHisInpatient(oracleList,
    // mysqlList);
    // return rs;
    // }
    //
    // @ResponseBody
    // @RequestMapping("/copyZyDetailCharge")
    // public int copyZyDetailCharge() {
    // List<ZyDetailCharge> oracleList =
    // this.zyDetailChargeService.getOracleList();
    // List<ZyDetailCharge> mysqlList =
    // this.zyDetailChargeService.getMysqlList();
    // int rs = this.zyDetailChargeService.copyMZyDetailCharge(oracleList,
    // mysqlList);
    // return rs;
    // }
    //
    // @ResponseBody
    // @RequestMapping("/copyZyChargeInfo")
    // public int copyZyChargeInfo() {
    // int rs = 0;
    // Integer mysqlId = null;
    //
    // // 查询错误列表信息(执行次数小于三次)
    // List<DataErrorInfo> errorList = this.errorInfoService.getExcuteList();
    // if (errorList != null && errorList.size() > 0) {
    // // 查询到没有操作成功的接口列表信息
    // List<ZyChargeInterface> excuteList =
    // this.zyChargeInfoService.getErrorCharge(errorList);
    // // 执行错误信息相关的数据
    // for (ZyChargeInterface zyChargeInfo : excuteList) {
    // mysqlId = zyChargeInfo.getId();
    // // 对重新对错误信息处理时进行try catch操作，如果出错则再次修改出错信息
    // try {
    // /*zyChargeInfo.setBxDate(zyChargeInfo.getBxDate().replace(".0", ""));
    // zyChargeInfo.setChargedate(zyChargeInfo.getChargedate().replace(".0",
    // ""));
    // zyChargeInfo.setOperatedate(zyChargeInfo.getOperatedate().replace(".0",
    // ""));
    // zyChargeInfo.setInvoiceChargedate(zyChargeInfo.getInvoiceChargedate().replace(".0",
    // ""));*/
    // this.zyChargeInfoService.insert(zyChargeInfo);
    // this.errorInfoService.update(mysqlId);
    // } catch (Exception e1) {
    // this.errorInfoService.updateError(mysqlId);
    // }
    // }
    // }
    // try {
    // List<DataErrorInfo> allErrorList = this.errorInfoService.getErrorList();
    // List<ZyChargeInterface> mysqlList =
    // this.zyChargeInfoService.getMysqlList(allErrorList);
    // List<ZyChargeInterface> oracleList =
    // this.zyChargeInfoService.getOracleList();
    //
    // for (ZyChargeInterface mysql : mysqlList) {
    // try {
    // mysqlId = mysql.getId();
    // rs += this.zyChargeInfoService.copyZyChargeInfo(mysql, oracleList);
    // } catch (Exception e) {
    // DataErrorInfo errorInfo = new DataErrorInfo();
    // errorInfo.setErrorMsg(e.getMessage());
    // errorInfo.setPrimaryKey(mysqlId);
    // errorInfo.setExcuteTime(1);
    // errorInfo.setHappenTime(new DateUtils().format(new Date()));
    // errorInfo.setTableName("his_charge_interface");
    // errorInfo.setIsSolved(0);
    // this.errorInfoService.addErrorInfo(errorInfo);
    // }
    // }
    // if (rs == oracleList.size()) {
    // return 1;
    // }
    // } catch (Exception e) {
    // DataErrorInfo errorInfo = new DataErrorInfo();
    // errorInfo.setErrorMsg(e.getMessage());
    // errorInfo.setExcuteTime(1);
    // errorInfo.setHappenTime(new DateUtils().format(new Date()));
    // errorInfo.setTableName("his_charge_interface");
    // errorInfo.setIsSolved(0);
    // this.errorInfoService.addErrorInfo(errorInfo);
    // }
    // return rs;
    // }
}
