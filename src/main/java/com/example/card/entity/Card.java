package com.example.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public class Card extends Model<Card> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.INPUT)
	private Integer id;
	@TableField("card_no")
	private String cardNo;
	@TableField("password")
	private String password;
	@TableField("type")
	private Integer type;
	@TableField("status")
	private Integer status;
	@TableField("agent_id")
	private Integer agentId;
	@TableField("customer_id")
	private Integer customerId;
	@TableField("active_time")
	private Date activeTime;
	@TableField("used_time")
	private Date usedTime;


	public Integer getId() {
		return id;
	}

	public Card setId(Integer id) {
		this.id = id;
		return this;
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

	public Card setPassword(String password) {
		this.password = password;
		return this;
	}

	public Integer getType() {
		return type;
	}

	public Card setType(Integer type) {
		this.type = type;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public Card setStatus(Integer status) {
		this.status = status;
		return this;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public Card setAgentId(Integer agentId) {
		this.agentId = agentId;
		return this;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public Card setCustomerId(Integer customerId) {
		this.customerId = customerId;
		return this;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public Card setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
		return this;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public Card setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
