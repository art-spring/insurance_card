package com.jcwenhua.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    @TableId("login_name")
    private String loginName;
    private String name;
    private String password;


    public String getLoginName() {
        return loginName;
    }

    public Admin setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Admin setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Admin setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginName;
    }

}
