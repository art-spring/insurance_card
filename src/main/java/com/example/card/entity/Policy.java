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
public class Policy extends Model<Policy> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	@TableField("card_id")
	private Integer cardId;
	private String holder;
	private String recognizee;
	@TableField("start_time")
	private Date startTime;
	@TableField("end_time")
	private Date endTime;
	@TableField("export_state")
	private Boolean exportState;


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

	public Boolean isExportState() {
		return exportState;
	}

	public Policy setExportState(Boolean exportState) {
		this.exportState = exportState;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
