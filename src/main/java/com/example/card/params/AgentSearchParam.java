package com.example.card.params;

import java.util.Date;

/**
 * Created by caichunyi on 2017/3/28.
 */
public class AgentSearchParam {
    private Date startTime;
    private Date endTime;
    private Integer state;
    private String keyword;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
