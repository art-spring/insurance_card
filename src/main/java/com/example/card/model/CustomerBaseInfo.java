package com.example.card.model;

import com.example.card.entity.Card;

import java.util.List;

/**
 * Created by racoon on 2017/5/3.
 */
public class CustomerBaseInfo {
    private String name;
    private String phoneNumber;
    private String headUrl;
    private List<Card> myLatestCards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public List<Card> getMyLatestCards() {
        return myLatestCards;
    }

    public void setMyLatestCards(List<Card> myLatestCards) {
        this.myLatestCards = myLatestCards;
    }
}
