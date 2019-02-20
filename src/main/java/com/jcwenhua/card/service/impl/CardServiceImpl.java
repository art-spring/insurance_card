package com.jcwenhua.card.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jcwenhua.card.entity.Card;
import com.jcwenhua.card.mapper.CardMapper;
import com.jcwenhua.card.model.CardDetailModel4Wechat;
import com.jcwenhua.card.model.CardInfoModel;
import com.jcwenhua.card.params.CardSearchParam;
import com.jcwenhua.card.service.CardService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {


    @Autowired
    private CardMapper cardMapper;

    @Override
    public boolean create(Card newCard) {
        Card param = new Card();
        param.setCardNo(newCard.getCardNo());
        Card searchEntity = this.cardMapper.selectOne(param);
        if (searchEntity == null) {
            newCard.setStatus(99);
            newCard.setCreatedTime(new Date());
            newCard.insert();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean useCard(Card card) {
        card.setStatus(2);
        card.setUsedTime(new Date());
        return card.updateById();
    }

    @Override
    public CardDetailModel4Wechat search4Wechat(int cardId) {
        return cardMapper.search4Wechat(cardId);
    }

    @Override
    public Card searchByCardNo(String cardNo) {
        Map<String, Object> param = new HashedMap();
        param.put("card_no", cardNo);
        List<Card> result = cardMapper.selectByMap(param);
        if (result != null && result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Page<CardInfoModel> search(CardSearchParam param) {

        Page<CardInfoModel> page = new Page<>(param.getPage(), param.getPageSize());
        if ("".equals(param.getKeyword())){
            param.setKeyword(null);
        }
        page.setRecords(this.cardMapper.query(page, param));
        return page;
    }
}
