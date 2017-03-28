package com.example.card.service.impl;

import com.example.card.entity.Card;
import com.example.card.entity.CardStaticInfo;
import com.example.card.mapper.CardMapper;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;
import com.example.card.service.CardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {


    @Autowired
    private CardMapper cardMapper;

    @Override
    public CardStaticInfo getStaticInfo() {
        return this.cardMapper.selectStaticInfo();
    }

    @Override
    public List<CardInfoModel> search(CardSearchParam param) {

        return this.cardMapper.search(param);
    }
}
