package com.jcwenhua.card.controller;

import com.jcwenhua.card.constants.HttpConstant;
import com.jcwenhua.card.entity.Admin;
import com.jcwenhua.card.interceptor.Auth;
import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.result.ResultCode;
import com.jcwenhua.card.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("getCurrentUser")
    public JSONResult<String> getCurrentUser(){
        JSONResult<String> result = new JSONResult<>();
        result.setResultCode(ResultCode.FAILD);
        result.setData("已登陆");
        return result;
    }


    @PostMapping(value = "/login")
    public JSONResult<String> login(@RequestBody Admin admin, HttpSession httpSession) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        JSONResult<String> result = new JSONResult<>();

        if (StringUtils.isEmpty(admin.getLoginName()) || StringUtils.isEmpty(admin.getPassword())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        Admin selectData = admin.selectById();
        if (selectData != null && MD5Util.equals(admin.getPassword(), selectData.getPassword())) {
            httpSession.setAttribute(HttpConstant.SESSION_DATA_KEY, admin);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setData("用户名或密码错误！");
        }
        return result;
    }

    @RequestMapping("/")
    public WebMvcProperties.View index() {
        return new WebMvcProperties.View();
    }

    @GetMapping(value = "/logout")
    @Auth
    public JSONResult<String> logout(HttpSession session) {

        JSONResult<String> result = new JSONResult<>();

        session.removeAttribute(HttpConstant.SESSION_DATA_KEY);

        return result;
    }


}
