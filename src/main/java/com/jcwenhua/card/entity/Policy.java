package com.jcwenhua.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jcwenhua.card.utils.DateJsonDeserializer;
import com.jcwenhua.card.utils.DateJsonSerializer;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@ExcelTarget("policy")
public class Policy extends Model<Policy> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	@TableField("card_id")
	private Integer cardId;
	@TableField("holder")
	private String holder;
	@TableField("holder_id_no")
	private String holderIdNo;
	@TableField("holder_address")
	private String holderAddress;
	@TableField("holder_birthday")
	@JsonDeserialize(using = DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date holderBirthday;
	@TableField("holder_gender")
	private String holderGender;
	@TableField("holder_occupation")
	private String holderOccupation;
	@TableField("holder_phone")
	private String holderPhone;
	@TableField("recognizee")
	private String recognizee;
	@TableField("recognizee_id_no")
	private String recognizeeIdNo;
	@TableField("recognizee_phone")
	private String recognizeePhone;
	@TableField("holder_recognizee_relation")
	private String holderRecognizeeRelation;
	@TableField("policy_number")
	private String policyNumber;
	@TableField("start_time")
	private String startTime;
	@TableField("end_time")
	private String endTime;
	@TableField("create_time")
	@JsonDeserialize(using = DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date createTime;
	@TableField("export_status")
	private Integer exportStatus;


	public Integer getId() {
		return id;
	}

	public Policy setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getCardId() {
		return cardId;
	}

	public Policy setCardId(Integer cardId) {
		this.cardId = cardId;
		return this;
	}

	public String getHolder() {
		return holder;
	}

	public Policy setHolder(String holder) {
		this.holder = holder;
		return this;
	}

	public String getRecognizee() {
		return recognizee;
	}

	public Policy setRecognizee(String recognizee) {
		this.recognizee = recognizee;
		return this;
	}

	public String getStartTime() {
		return startTime;
	}

	public Policy setStartTime(String startTime) {
		this.startTime = startTime;
		return this;
	}

	public String getEndTime() {
		return endTime;
	}

	public Policy setEndTime(String endTime) {
		this.endTime = endTime;
		return this;
	}

	public Integer getExportStatus() {
		return exportStatus;
	}

	public void setExportStatus(Integer exportStatus) {
		this.exportStatus = exportStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHolderIdNo() {
		return holderIdNo;
	}

	public void setHolderIdNo(String holderIdNo) {
		this.holderIdNo = holderIdNo;
	}

	public String getRecognizeeIdNo() {
		return recognizeeIdNo;
	}

	public void setRecognizeeIdNo(String recognizeeIdNo) {
		this.recognizeeIdNo = recognizeeIdNo;
	}

	public String getHolderPhone() {
		return holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	public String getRecognizeePhone() {
		return recognizeePhone;
	}

	public void setRecognizeePhone(String recognizeePhone) {
		this.recognizeePhone = recognizeePhone;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
