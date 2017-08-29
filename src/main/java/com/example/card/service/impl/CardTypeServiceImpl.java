package com.example.card.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.CardType;
import com.example.card.mapper.CardTypeMapper;
import com.example.card.service.CardTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@Service
public class CardTypeServiceImpl extends ServiceImpl<CardTypeMapper, CardType> implements CardTypeService {

    @Override
    public Map<String, Integer> getNameIdMap() {
        List<CardType> cardTypes = this.selectList(new EntityWrapper<>(null));
        Map<String, Integer> cardTypeMap = new HashMap<>();
        for (CardType cardType : cardTypes) {
            cardTypeMap.put(cardType.getName(), cardType.getId());
        }
        return cardTypeMap;
    }

    @Override
    public List<CardType> getAllCardType() {
        return this.selectList(null);
    }

    @Override
    public CardType getCardTypeByTypeName(String typeName) {
        Map<String, Object> param = new HashedMap();
        param.put("name", typeName);
        List<CardType> result = this.selectByMap(param);
        if (result != null && result.size() == 1) {
            return result.get(0);
        }
        return null;
    }


}
