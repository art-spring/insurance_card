package com.example.card.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.example.card.entity.Card;
import com.example.card.model.CardDetailModel4Wechat;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface CardService extends IService<Card> {

    Page<CardInfoModel> search(@NotNull CardSearchParam param);

    boolean create(Card newCard);

    boolean useCard(Card card);

    CardDetailModel4Wechat search4Wechat(int cardId);

    Card searchByCardNo(String cardNo);

}
