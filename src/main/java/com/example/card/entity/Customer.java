package com.example.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	@TableField("id_number")
	private String idNumber;
	@TableField("wx_id")
	private String wxId;
	@TableField("phone_number")
	private String phoneNumber;
	private String name;
	@TableField("nick_name")
	private String nickName;
	private String address;
	@TableField("bind_state")
	private Boolean bindState;
	@TableField("bind_time")
	private Date bindTime;
	@TableField("unbind_time")
	private Date unbindTime;


	public Integer getId() {
		return id;
	}

	public Customer setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public Customer setIdNumber(String idNumber) {
		this.idNumber = idNumber;
		return this;
	}

	public String getWxId() {
		return wxId;
	}

	public Customer setWxId(String wxId) {
		this.wxId = wxId;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Customer setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public String getName() {
		return name;
	}

	public Customer setName(String name) {
		this.name = name;
		return this;
	}

	public String getNickName() {
		return nickName;
	}

	public Customer setNickName(String nickName) {
		this.nickName = nickName;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Customer setAddress(String address) {
		this.address = address;
		return this;
	}

	public Boolean isBindState() {
		return bindState;
	}

	public Customer setBindState(Boolean bindState) {
		this.bindState = bindState;
		return this;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public Customer setBindTime(Date bindTime) {
		this.bindTime = bindTime;
		return this;
	}

	public Date getUnbindTime() {
		return unbindTime;
	}

	public Customer setUnbindTime(Date unbindTime) {
		this.unbindTime = unbindTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
