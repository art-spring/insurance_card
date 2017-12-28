package com.jcwenhua.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.jcwenhua.card.utils.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
public class Agent extends Model<Agent> {

    private static final long serialVersionUID = 1L;


	private Integer id;
	@TableField("wx_id")
	private String wxId;
	@TableField("phone_number")
	private String phoneNumber;
	private String name;
	@TableField("apply_type")
	private Integer applyType;
	private Integer status;
	@TableField("create_type")
	private Integer createType;
	@TableField("create_time")
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date createTime;
	@TableField("bind_time")
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date bindTime;
	@TableField("unbind_time")
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date unbindTime;


	public Integer getId() {
		return id;
	}

	public Agent setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getWxId() {
		return wxId;
	}

	public Agent setWxId(String wxId) {
		this.wxId = wxId;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Agent setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public String getName() {
		return name;
	}

	public Agent setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public Agent setApplyType(Integer applyType) {
		this.applyType = applyType;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateType() {
		return createType;
	}

	public Agent setCreateType(Integer createType) {
		this.createType = createType;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Agent setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public Agent setBindTime(Date bindTime) {
		this.bindTime = bindTime;
		return this;
	}

	public Date getUnbindTime() {
		return unbindTime;
	}

	public Agent setUnbindTime(Date unbindTime) {
		this.unbindTime = unbindTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
