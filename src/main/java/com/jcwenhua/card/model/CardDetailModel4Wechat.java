package com.jcwenhua.card.model;

import com.jcwenhua.card.utils.DateJsonDeserializer;
import com.jcwenhua.card.utils.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by racoon on 2017/5/11.
 */
public class CardDetailModel4Wechat {
    private Integer id;
    private String cardNo;
    private String password;
    private Integer status;
    private Integer type;
    private String typeName;
    private Integer agentId;
    private String agentName;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date activeTime;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date usedTime;
    private Integer policyId;
    private String holder;
    private String holderIdNo;
    private String holderAddress;
    private Integer holderGender;
    private String holderOccupation;
    private String holderPhone;
    private String recognizee;
    private String recognizeeIdNo;
    private String recognizeePhone;
    private Integer recognizeeGender;
    private String policyNumber;
    private String holderRecognizeeRelation;
    private String policyStartTime;
    private String policyEndTime;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date policyCreateTime;
    private Integer policyExportStatus;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getRecognizee() {
        return recognizee;
    }

    public void setRecognizee(String recognizee) {
        this.recognizee = recognizee;
    }

    public String getPolicyStartTime() {
        return policyStartTime;
    }

    public void setPolicyStartTime(String policyStartTime) {
        this.policyStartTime = policyStartTime;
    }

    public String getPolicyEndTime() {
        return policyEndTime;
    }

    public void setPolicyEndTime(String policyEndTime) {
        this.policyEndTime = policyEndTime;
    }

    public Date getPolicyCreateTime() {
        return policyCreateTime;
    }

    public void setPolicyCreateTime(Date policyCreateTime) {
        this.policyCreateTime = policyCreateTime;
    }

    public Integer getPolicyExportStatus() {
        return policyExportStatus;
    }

    public void setPolicyExportStatus(Integer policyExportStatus) {
        this.policyExportStatus = policyExportStatus;
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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getHolderAddress() {
        return holderAddress;
    }

    public void setHolderAddress(String holderAddress) {
        this.holderAddress = holderAddress;
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

    public Integer getRecognizeeGender() {
        return recognizeeGender;
    }

    public void setRecognizeeGender(Integer recognizeeGender) {
        this.recognizeeGender = recognizeeGender;
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
}
