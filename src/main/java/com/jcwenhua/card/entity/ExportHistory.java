package com.jcwenhua.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@TableName("export_history")
public class ExportHistory extends Model<ExportHistory> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	@TableField("policy_id")
	private Integer policyId;
	@TableField("export_time")
	private Date exportTime;


	public Integer getId() {
		return id;
	}

	public ExportHistory setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public ExportHistory setPolicyId(Integer policyId) {
		this.policyId = policyId;
		return this;
	}

	public Date getExportTime() {
		return exportTime;
	}

	public ExportHistory setExportTime(Date exportTime) {
		this.exportTime = exportTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
