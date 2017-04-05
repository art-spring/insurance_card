package com.example.card.params;

import com.example.card.utils.DateJsonDeserializer;
import com.example.card.utils.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by caichunyi on 2017/3/16.
 */
public class CardSearchParam {

    private Integer page;
    private Integer pageSize;
    private Integer status;
    private String agentName;
    private String keyword;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date applyDateBegin;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date applyDateEnd;
    private Integer type;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
