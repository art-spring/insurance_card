package com.jcwenhua.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.jcwenhua.card.utils.DateJsonDeserializer;
import com.jcwenhua.card.utils.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jeecgframework.poi.excel.annotation.Excel;
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
	@Excel(name = "投保人", orderNum = "1")
	private String holder;
	@TableField("holder_id_no")
	@Excel(name = "投保人身份证号", orderNum = "2")
	private String holderIdNo;
	@TableField("holder_address")
	@Excel(name = "投保地区（地级市）", orderNum = "3")
	private String holderAddress;
	@TableField("holder_birthday")
	@Excel(name = "投保人生日", orderNum = "4")
	@JsonDeserialize(using = DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date holderBirthday;
	@TableField("holder_gender")
	@Excel(name = "投保人性别", orderNum = "5" ,replace = {"男_0", "女_1"})
	private Integer holderGender;
	@TableField("holder_occupation")
	@Excel(name = "投保人职业类别", orderNum = "6")
	private String holderOccupation;
	@TableField("holder_phone")
	@Excel(name = "投保人手机号", orderNum = "7")
	private String holderPhone;
	@TableField("recognizee")
	@Excel(name = "被投保人", orderNum = "8")
	private String recognizee;
	@TableField("recognizee_id_no")
	@Excel(name = "被投保人身份证号", orderNum = "9")
	private String recognizeeIdNo;
	@TableField("recognizee_phone")
	@Excel(name = "被投保人手机号", orderNum = "10")
	private String recognizeePhone;
	@TableField("holder_recognizee_relation")
	@Excel(name = "关系", orderNum = "11",replace = {"本人_1", "母亲_2", "父亲_3", "儿子_4", "女儿_5", "配偶_6"})
	private String holderRecognizeeRelation;
	@Excel(name = "生效时间", orderNum = "12", format = "yyyy-MM-dd")
	@TableField("start_time")
	@JsonDeserialize(using = DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date startTime;
	@Excel(name = "失效时间", orderNum = "13", format = "yyyy-MM-dd")
	@TableField("end_time")
	@JsonDeserialize(using = DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date endTime;
	@Excel(name = "创建时间", orderNum = "14", format = "yyyy-MM-dd")
	@TableField("create_time")
	@JsonDeserialize(using = DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date createTime;
	@Excel(name = "导出状态", orderNum = "15", replace = {"未导出_0", "已导出_1"})
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

	public Date getStartTime() {
		return startTime;
	}

	public Policy setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Policy setEndTime(Date endTime) {
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
