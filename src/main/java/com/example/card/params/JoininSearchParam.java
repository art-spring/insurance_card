package com.example.card.params;

import com.example.card.utils.DateJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * Created by racoon on 2017/4/10.
 */
public class JoininSearchParam {
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date applyDateBegin;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date applyDateEnd;
    private Integer state;
    private String keyword;

    public Date getApplyDateBegin() {
        return applyDateBegin;
    }

    public void setApplyDateBegin(Date applyDateBegin) {
        this.applyDateBegin = applyDateBegin;
    }

    public Date getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(Date applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
