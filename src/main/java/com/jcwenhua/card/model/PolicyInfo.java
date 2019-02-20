package com.jcwenhua.card.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jcwenhua.card.utils.DateJsonDeserializer;
import com.jcwenhua.card.utils.DateJsonSerializer;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.util.Date;

/**
 * Created by racoon on 2017/8/30.
 */
@ExcelTarget("policyInfo")
public class PolicyInfo {

    @Excel(name = "投保编号id（请勿修改）", orderNum = "1")
    private Integer id;
//    private Integer cardId;
    @Excel(name = "卡片编号", orderNum = "1")
    private String cardNo;
//    @Excel(name = "卡片密码", orderNum = "1")
//    private String cardPassword;
//    @Excel(name = "卡片类型", orderNum = "1", replace = {"A类_1", "B类_2", "C类_3", "D类_4", "E类_5"})
    private String cardType;
    @Excel(name = "投保人", orderNum = "1")
    private String holder;
    @Excel(name = "投保人身份证号", orderNum = "2")
    private String holderIdNo;
    @Excel(name = "投保地区（地级市）", orderNum = "3")
    private String holderAddress;
    @Excel(name = "投保人生日", orderNum = "4")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date holderBirthday;
    @Excel(name = "投保人性别", orderNum = "4" ,replace = {"男_0", "女_1"})
    private String holderGender;
    @Excel(name = "投保人职业类别", orderNum = "4")
    private String holderOccupation;
    @Excel(name = "投保人手机号", orderNum = "4")
    private String holderPhone;
    @Excel(name = "被投保人", orderNum = "4")
    private String recognizee;
    @Excel(name = "被投保人证件号", orderNum = "5")
    private String recognizeeIdNo;
    @Excel(name = "被投保人手机号", orderNum = "6")
    private String recognizeePhone;
    @Excel(name = "关系", orderNum = "6",replace = {"本人_1", "母亲_2", "父亲_3", "儿子_4", "女儿_5", "配偶_6"})
    private String holderRecognizeeRelation;
    @Excel(name = "保单号", orderNum = "6")
    private String policyNumber;
    @Excel(name = "生效时间", orderNum = "7")
    private String startTime;
    @Excel(name = "保险期限", orderNum = "8")
    private String endTime;
    @Excel(name = "信息录入时间", orderNum = "9", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date createTime;
    private Integer exportStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getHolderIdNo() {
        return holderIdNo;
    }

    public void setHolderIdNo(String holderIdNo) {
        this.holderIdNo = holderIdNo;
    }

    public String getHolderPhone() {
        return holderPhone;
    }

    public void setHolderPhone(String holderPhone) {
        this.holderPhone = holderPhone;
    }

    public String getRecognizee() {
        return recognizee;
    }

    public void setRecognizee(String recognizee) {
        this.recognizee = recognizee;
    }

    public String getRecognizeeIdNo() {
        return recognizeeIdNo;
    }

    public void setRecognizeeIdNo(String recognizeeIdNo) {
        this.recognizeeIdNo = recognizeeIdNo;
    }

    public String getRecognizeePhone() {
        return recognizeePhone;
    }

    public void setRecognizeePhone(String recognizeePhone) {
        this.recognizeePhone = recognizeePhone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHolderAddress() {
        return holderAddress;
    }

    public void setHolderAddress(String holderAddress) {
        this.holderAddress = holderAddress;
    }

    public Date getHolderBirthday() {
        return holderBirthday;
    }

    public void setHolderBirthday(Date holderBirthday) {
        this.holderBirthday = holderBirthday;
    }

    public String getHolderGender() {
        return holderGender;
    }

    public void setHolderGender(String holderGender) {
        this.holderGender = holderGender;
    }

    public String getHolderOccupation() {
        return holderOccupation;
    }

    public void setHolderOccupation(String holderOccupation) {
        this.holderOccupation = holderOccupation;
    }

    public String getHolderRecognizeeRelation() {
        return holderRecognizeeRelation;
    }

    public void setHolderRecognizeeRelation(String holderRecognizeeRelation) {
        this.holderRecognizeeRelation = holderRecognizeeRelation;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Integer getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Integer exportStatus) {
        this.exportStatus = exportStatus;
    }
}
