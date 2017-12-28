package com.jcwenhua.card.params;

import com.jcwenhua.card.utils.DateJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * Created by caichunyi on 2017/3/16.
 */
public class CardSearchParam {

    private Integer page = 1;
    private Integer pageSize = 10;
    private Integer status;
    private String startCardNo;
    private String endCardNo;
    private Integer agentId;
    private String agentName;
    private String keyword;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date applyDateBegin;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date applyDateEnd;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date grantDateBegin;
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date grantDateEnd;

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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
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

    public Date getGrantDateBegin() {
        return grantDateBegin;
    }

    public void setGrantDateBegin(Date grantDateBegin) {
        this.grantDateBegin = grantDateBegin;
    }

    public Date getGrantDateEnd() {
        return grantDateEnd;
    }

    public void setGrantDateEnd(Date grantDateEnd) {
        this.grantDateEnd = grantDateEnd;
    }

    public String getStartCardNo() {
        return startCardNo;
    }

    public void setStartCardNo(String startCardNo) {
        this.startCardNo = startCardNo;
    }

    public String getEndCardNo() {
        return endCardNo;
    }

    public void setEndCardNo(String endCardNo) {
        this.endCardNo = endCardNo;
    }
}
