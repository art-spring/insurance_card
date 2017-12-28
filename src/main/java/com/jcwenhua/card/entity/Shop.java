package com.jcwenhua.card.entity;

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
public class Shop extends Model<Shop> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String url;
	private String description;
	private Integer type;
	@TableField("joinin_id")
	private Integer joininId;
	@TableField("area_id")
	private Integer areaId;
	@TableField("create_time")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public Shop setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Shop setName(String name) {
		this.name = name;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Shop setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Shop setDescription(String description) {
		this.description = description;
		return this;
	}

	public Integer getType() {
		return type;
	}

	public Shop setType(Integer type) {
		this.type = type;
		return this;
	}

	public Integer getJoininId() {
		return joininId;
	}

	public Shop setJoininId(Integer joininId) {
		this.joininId = joininId;
		return this;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public Shop setAreaId(Integer areaId) {
		this.areaId = areaId;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Shop setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
