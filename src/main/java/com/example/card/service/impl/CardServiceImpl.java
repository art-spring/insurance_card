package com.example.card.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
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
 * 服务实现类
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
    public boolean create(Card newCard) {
        Card param = new Card();
        param.setCardNo(newCard.getCardNo());
        Card searchEntity = this.cardMapper.selectOne(param);
        if (searchEntity == null) {
            newCard.setStatus(0);
            newCard.insert();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<CardInfoModel> search(CardSearchParam param) {

        Page<CardInfoModel> page = new Page<>(param.getPage(), param.getPageSize());

        page.setRecords(this.cardMapper.search(page, param));
        return page;
    }
}
