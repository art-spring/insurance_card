package com.example.card.service;

import com.example.card.entity.Card;
import com.baomidou.mybatisplus.service.IService;
import com.example.card.entity.CardStaticInfo;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface CardService extends IService<Card> {
    CardStaticInfo getStaticInfo();

    List<CardInfoModel> search(@NotNull CardSearchParam param);
	
}
