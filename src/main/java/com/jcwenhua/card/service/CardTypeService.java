package com.jcwenhua.card.service;

import com.baomidou.mybatisplus.service.IService;
import com.jcwenhua.card.entity.CardType;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface CardTypeService extends IService<CardType> {

    Map<String, Integer> getNameIdMap();

    List<CardType> getAllCardType();

    CardType getCardTypeByTypeName(String typeName);

}
