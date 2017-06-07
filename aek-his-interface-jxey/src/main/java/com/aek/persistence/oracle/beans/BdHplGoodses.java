package com.aek.persistence.oracle.beans;

import com.mb.common.dal.object.AbstractDO;

/**
 * 物资基础数据表。模型
 * @author Lanpz
 * @date 2016-12-15
 * @source Generated by MySQL2Java
 */
public class BdHplGoodses extends AbstractDO {

    private Integer areaId;

    private Long sysId;

    private Long classId;

    private String className;

    private Long goodsId;

    private Long typeId;
    
    private String typeName;

    private String goodsGuid;

    private Integer goodsType;
    
    private String goodsNo;

    private String goodsName;

    private String goodsNamePy;

    private String goodsNameWb;

    private Long goodsAccountTypeId;

    private String goodsAccountName;

    private Long goodsCalcTypeId;

    private String goodsCalcName;

    private String goodsStandardTypeId;

    private String goodsStandardName;

    private Integer goodsManagementFlag;

    private Long facId;
    
    private String facName;

    private String factoryAddr;

    private Long brandId;
    
    private String brandName;

    private Long regId;
    
    private String regNo;

    private Long goodsMaterialId;
    
    private String goodsMaterialName;

    private Long goodsSterilizeId;
    
    private String goodsSterilizeName;

    private Integer medicareFlag;

    private Integer repeatFlag;

    private Integer sourceFlag;

    private Long addBy;

    private String addName;

    private String addTime;

    private Long lastEditBy;

    private String lastEditName;

    private String lastEditTime;

    private Integer delFlag;

    public String getFacName() {
        return facName;
    }

    public void setFacName(String facName) {
        this.facName = facName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getGoodsGuid() {
        return goodsGuid;
    }

    public void setGoodsGuid(String goodsGuid) {
        this.goodsGuid = goodsGuid;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNamePy() {
        return goodsNamePy;
    }

    public void setGoodsNamePy(String goodsNamePy) {
        this.goodsNamePy = goodsNamePy;
    }

    public String getGoodsNameWb() {
        return goodsNameWb;
    }

    public void setGoodsNameWb(String goodsNameWb) {
        this.goodsNameWb = goodsNameWb;
    }

    public Long getGoodsAccountTypeId() {
        return goodsAccountTypeId;
    }

    public void setGoodsAccountTypeId(Long goodsAccountTypeId) {
        this.goodsAccountTypeId = goodsAccountTypeId;
    }

    public String getGoodsAccountName() {
        return goodsAccountName;
    }

    public void setGoodsAccountName(String goodsAccountName) {
        this.goodsAccountName = goodsAccountName;
    }

    public Long getGoodsCalcTypeId() {
        return goodsCalcTypeId;
    }

    public void setGoodsCalcTypeId(Long goodsCalcTypeId) {
        this.goodsCalcTypeId = goodsCalcTypeId;
    }

    public String getGoodsCalcName() {
        return goodsCalcName;
    }

    public void setGoodsCalcName(String goodsCalcName) {
        this.goodsCalcName = goodsCalcName;
    }

    public String getGoodsStandardTypeId() {
        return goodsStandardTypeId;
    }

    public void setGoodsStandardTypeId(String goodsStandardTypeId) {
        this.goodsStandardTypeId = goodsStandardTypeId;
    }

    public String getGoodsStandardName() {
        return goodsStandardName;
    }

    public void setGoodsStandardName(String goodsStandardName) {
        this.goodsStandardName = goodsStandardName;
    }

    public Integer getGoodsManagementFlag() {
        return goodsManagementFlag;
    }

    public void setGoodsManagementFlag(Integer goodsManagementFlag) {
        this.goodsManagementFlag = goodsManagementFlag;
    }

    public Long getFacId() {
        return facId;
    }

    public void setFacId(Long facId) {
        this.facId = facId;
    }

    public String getFactoryAddr() {
        return factoryAddr;
    }

    public void setFactoryAddr(String factoryAddr) {
        this.factoryAddr = factoryAddr;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    public Long getGoodsMaterialId() {
        return goodsMaterialId;
    }

    public void setGoodsMaterialId(Long goodsMaterialId) {
        this.goodsMaterialId = goodsMaterialId;
    }

    public Long getGoodsSterilizeId() {
        return goodsSterilizeId;
    }

    public void setGoodsSterilizeId(Long goodsSterilizeId) {
        this.goodsSterilizeId = goodsSterilizeId;
    }

    public Integer getMedicareFlag() {
        return medicareFlag;
    }

    public void setMedicareFlag(Integer medicareFlag) {
        this.medicareFlag = medicareFlag;
    }

    public Integer getRepeatFlag() {
        return repeatFlag;
    }

    public void setRepeatFlag(Integer repeatFlag) {
        this.repeatFlag = repeatFlag;
    }

    public Integer getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(Integer sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public Long getAddBy() {
        return addBy;
    }

    public void setAddBy(Long addBy) {
        this.addBy = addBy;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getLastEditBy() {
        return lastEditBy;
    }

    public void setLastEditBy(Long lastEditBy) {
        this.lastEditBy = lastEditBy;
    }

    public String getLastEditName() {
        return lastEditName;
    }

    public void setLastEditName(String lastEditName) {
        this.lastEditName = lastEditName;
    }

    public String getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getGoodsMaterialName() {
        return goodsMaterialName;
    }

    public void setGoodsMaterialName(String goodsMaterialName) {
        this.goodsMaterialName = goodsMaterialName;
    }

    public String getGoodsSterilizeName() {
        return goodsSterilizeName;
    }

    public void setGoodsSterilizeName(String goodsSterilizeName) {
        this.goodsSterilizeName = goodsSterilizeName;
    }

}
