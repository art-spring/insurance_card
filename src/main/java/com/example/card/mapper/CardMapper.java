package com.example.card.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Card;
import com.example.card.model.CardDetailModel4Wechat;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface CardMapper extends BaseMapper<Card> {

    List<CardInfoModel> query(Page<CardInfoModel> page, CardSearchParam param);

    CardDetailModel4Wechat search4Wechat(int cardId);
}