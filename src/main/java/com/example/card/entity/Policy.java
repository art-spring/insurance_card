package com.example.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.example.card.utils.DateJsonSerializer;
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
	@Excel(name = "投保人", orderNum = "1")
	private String holder;
	@Excel(name = "被投保人", orderNum = "2")
	private String recognizee;
	@Excel(name = "生效时间", orderNum = "3", format = "yyyy-MM-dd")
	@TableField("start_time")
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date startTime;
	@Excel(name = "失效时间", orderNum = "4", format = "yyyy-MM-dd")
	@TableField("end_time")
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date endTime;
	@Excel(name = "创建时间", orderNum = "5", format = "yyyy-MM-dd")
	@TableField("create_time")
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date createTime;
	@Excel(name = "导出状态", orderNum = "6", replace = {"未导出_0", "已导出_1"})
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
