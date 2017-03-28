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
@TableName("card_type")
public class CardType extends Model<CardType> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;


	public Integer getId() {
		return id;
	}

	public CardType setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public CardType setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
