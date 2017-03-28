package com.example.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
@TableName("shop_type")
public class ShopType extends Model<ShopType> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;


	public Integer getId() {
		return id;
	}

	public ShopType setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ShopType setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
