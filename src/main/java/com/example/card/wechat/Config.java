package com.example.card.wechat;

/**
 * Created by racoon on 2017/4/17.
 */

import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wechat")
public class Config {

    @Value("${wechat.token}")
    private String token;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Value("${wechat.aesKey}")
    private String aesKey;

    private ApiConfig apiConfig;

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAesKey() {
        return aesKey;
    }

    public ApiConfig getApiConfig() {
        if (apiConfig == null){
            apiConfig = new ApiConfig(getAppId(),getAppSecret(),true);
        }
        return apiConfig;
    }
}