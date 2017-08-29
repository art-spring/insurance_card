package com.example.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.example.card.utils.DateJsonDeserializer;
import com.example.card.utils.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public class Card extends Model<Card> {

    private static final long serialVersionUID = 1L;
    @Excel(name = "ID", orderNum = "1")
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    @Excel(name = "卡片编号", orderNum = "2")
    @TableField("card_no")
    private String cardNo;
    @Excel(name = "卡片密码", orderNum = "3")
    @TableField("password")
    private String password;
    @Excel(name = "卡片类型", orderNum = "4", replace = {"A_1", "B_2", "C_3", "D_4", "E_5"}, suffix = "类")
    @TableField("type")
    private Integer type;
    @Excel(name = "卡片状态", orderNum = "5", replace = {"新建_99", "未使用_0", "已激活_1", "已使用_2"})
    @TableField("status")
    private Integer status;
    @TableField("agent_id")
    private Integer agentId;
    @TableField("customer_id")
    private Integer customerId;
    @Excel(name = "创建时间", orderNum = "6", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    @TableField("created_time")
    private Date createdTime;
    @Excel(name = "激活时间", orderNum = "7", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    @TableField("active_time")
    private Date activeTime;
    @Excel(name = "使用时间", orderNum = "8", format = "yyyy-MM-dd")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    @TableField("used_time")
    private Date usedTime;


    public Integer getId() {
        return id;
    }

    public Card setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPassword() {
        return password;
    }

    public Card setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Card setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Card setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public Card setAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Card setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public Card setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
        return this;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public Card setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
