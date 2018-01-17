package com.jcwenhua.card.model;

import com.jcwenhua.card.utils.DateJsonDeserializer;
import com.jcwenhua.card.utils.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.util.Date;

/**
 * Created by racoon on 2017/8/30.
 */
@ExcelTarget("policyInfo")
public class PolicyInfo {

    private Integer id;
    private Integer cardId;
    @Excel(name = "卡片编号", orderNum = "1")
    private String cardNo;
    @Excel(name = "卡片密码", orderNum = "1")
    private String cardPassword;
    @Excel(name = "卡片类型", orderNum = "1", replace = {"A类_1", "B类_2", "C类_3", "D类_4", "E类_5"})
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
    private Integer holderGender;
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
    @Excel(name = "生效时间", orderNum = "7", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date startTime;
    @Excel(name = "失效时间", orderNum = "8", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date endTime;
    @Excel(name = "创建时间", orderNum = "9", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date createTime;
    @Excel(name = "导出状态", orderNum = "10", replace = {"未导出_0", "已导出_1"})
    private Integer exportStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Integer exportStatus) {
        this.exportStatus = exportStatus;
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

    public Integer getHolderGender() {
        return holderGender;
    }

    public void setHolderGender(Integer holderGender) {
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
}
