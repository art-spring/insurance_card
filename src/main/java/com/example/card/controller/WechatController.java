package com.example.card.controller;

/**
 * Created by racoon on 2017/4/17.
 */

import com.example.card.wechat.Config;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/weixin")
public class WechatController extends WeixinControllerSupport {
    private static final Logger log = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private Config config;


    @Value("${wechat.redirect.joinin.url}")
    private String redirectJoininUrl;
    @Value("${wechat.redirect.joinin.param.userId}")
    private String redirectJoininUserId;
    @Value("${wechat.redirect.joinin.param.appId}")
    private String redirectJoininAppId;
    @Value("${wechat.redirect.joinin.param.jsapiTicket}")
    private String redirectJoininJsapiTicket;
    @Value("${wechat.redirect.joinin.param.openId}")
    private String redirectJoininOpenId;

    @Value("${wechat.redirect.info.url}")
    private String redirectInfoUrl;
    @Value("${wechat.redirect.info.param.userId}")
    private String redirectInfoUserId;
    @Value("${wechat.redirect.info.param.appId}")
    private String redirectInfoAppId;
    @Value("${wechat.redirect.info.param.jsapiTicket}")
    private String redirectInfoJsapiTicket;
    @Value("${wechat.redirect.info.param.openId}")
    private String redirectInfoOpenId;


    @Value("${wechat.redirect.activecard.url}")
    private String redirectActivecardUrl;
    @Value("${wechat.redirect.activecard.param.userId}")
    private String redirectActivecardUserId;
    @Value("${wechat.redirect.activecard.param.appId}")
    private String redirectActivecardAppId;
    @Value("${wechat.redirect.activecard.param.jsapiTicket}")
    private String redirectActivecardJsapiTicket;
    @Value("${wechat.redirect.activecard.param.openId}")
    private String redirectActivecardOpenId;


    @Value("${wechat.redirect.agent.url}")
    private String redirectAgentUrl;
    @Value("${wechat.redirect.agent.param.userId}")
    private String redirectAgentUserId;
    @Value("${wechat.redirect.agent.param.appId}")
    private String redirectAgentAppId;
    @Value("${wechat.redirect.agent.param.jsapiTicket}")
    private String redirectAgentJsapiTicket;
    @Value("${wechat.redirect.agent.param.openId}")
    private String redirectAgentOpenId;


    //设置TOKEN，用于绑定微信服务器
    @Override
    protected String getToken() {
        return config.getToken();
    }

    //使用安全模式时设置：APPID
    //不再强制重写，有加密需要时自行重写该方法
    @Override
    protected String getAppId() {
        return config.getAppId();
    }

    //使用安全模式时设置：密钥
    //不再强制重写，有加密需要时自行重写该方法
    @Override
    protected String getAESKey() {
        return config.getAesKey();
    }

    // 关注
    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {
        return new TextMsg("test");
    }

    // 取消关注
    @Override
    protected BaseMsg handleUnsubscribe(BaseEvent event) {
        return null;
    }

    //重写父类方法，处理对应的微信消息
    @Override
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        String content = msg.getContent();
        String openId = msg.getFromUserName();
        String response = "请选择相应功能";
        return new TextMsg(response);
    }

    @RequestMapping("/redirectAgent")
    public ModelAndView handleRedirectAgent(@RequestParam("code") String code) {
        String redirect = "redirect:" + redirectAgentUrl;

//        OauthAPI oauthAPI = new OauthAPI(config.getApiConfig());
//        OauthGetTokenResponse response = oauthAPI.getToken(code);
//
//        String openid = response.getOpenid();
//        if (StringUtils.isEmpty(openid)) {
//            return new ModelAndView(redirect);
//        }
//
//        ApiResult result = dubboService.getWechatUser(openid);
//        if (ResultCode.ERROR.getCode() == result.getCode()) {
//            UserAPI userAPI = new UserAPI(config.getApiConfig());
//            GetUserInfoResponse userInfo = userAPI.getUserInfo(openid);
//            if (StringUtils.isEmpty(userInfo.getOpenid())) {
//                return new ModelAndView(redirect);
//            }
//
//            result = dubboService.createWechatUser(userInfo);
//            if (ResultCode.ERROR.getCode() == result.getCode()) {
//                return new ModelAndView(redirect);
//            }
//        }
//
//        Integer wechatUserId = ((WechatUserOutput)result.getData()).getWechatUserId();
//
//        String  jsApiTicket = config.getApiConfig().getJsApiTicket();
//
//        redirect += "?" + redirectToutiaoParamUserId + "=" + String.valueOf(wechatUserId);
//        redirect += "&" + redirectToutiaoParamAppId + "=" + config.getApiConfig().getAppid();
//        redirect += "&" + redirectToutiaoParamJsapiTicket + "=" + jsApiTicket
//        redirect += "&" + redirectToutiaoParamOpenId + "=" + openid;

        return new ModelAndView(redirect);
    }

    @RequestMapping("/redirectActivecard")
    public ModelAndView handleRedirectActivecard(@RequestParam("code") String code) {
        String redirect = "redirect:" + redirectActivecardUrl;

//        OauthAPI oauthAPI = new OauthAPI(config.getApiConfig());
//        OauthGetTokenResponse response = oauthAPI.getToken(code);
//
//        String openid = response.getOpenid();
//        if (StringUtils.isEmpty(openid)) {
//            return new ModelAndView(redirect);
//        }
//
//        ApiResult result = dubboService.getWechatUser(openid);
//        if (ResultCode.ERROR.getCode() == result.getCode()) {
//            UserAPI userAPI = new UserAPI(config.getApiConfig());
//            GetUserInfoResponse userInfo = userAPI.getUserInfo(openid);
//            if (StringUtils.isEmpty(userInfo.getOpenid())) {
//                return new ModelAndView(redirect);
//            }
//
//            result = dubboService.createWechatUser(userInfo);
//            if (ResultCode.ERROR.getCode() == result.getCode()) {
//                return new ModelAndView(redirect);
//            }
//        }
//
//        Integer wechatUserId = ((WechatUserOutput)result.getData()).getWechatUserId();
//
//        String  jsApiTicket = config.getApiConfig().getJsApiTicket();
//
//        redirect += "?" + redirectToutiaoParamUserId + "=" + String.valueOf(wechatUserId);
//        redirect += "&" + redirectToutiaoParamAppId + "=" + config.getApiConfig().getAppid();
//        redirect += "&" + redirectToutiaoParamJsapiTicket + "=" + jsApiTicket
//        redirect += "&" + redirectToutiaoParamOpenId + "=" + openid;

        return new ModelAndView(redirect);
    }

    @RequestMapping("/redirectJoinin")
    public ModelAndView handleRedirectJoinin(@RequestParam("code") String code) {
        String redirect = "redirect:" + redirectJoininUrl;

//        OauthAPI oauthAPI = new OauthAPI(config.getApiConfig());
//        OauthGetTokenResponse response = oauthAPI.getToken(code);
//
//        String openid = response.getOpenid();
//        if (StringUtils.isEmpty(openid)) {
//            return new ModelAndView(redirect);
//        }
//
//        ApiResult result = dubboService.getWechatUser(openid);
//        if (ResultCode.ERROR.getCode() == result.getCode()) {
//            UserAPI userAPI = new UserAPI(config.getApiConfig());
//            GetUserInfoResponse userInfo = userAPI.getUserInfo(openid);
//            if (StringUtils.isEmpty(userInfo.getOpenid())) {
//                return new ModelAndView(redirect);
//            }
//
//            result = dubboService.createWechatUser(userInfo);
//            if (ResultCode.ERROR.getCode() == result.getCode()) {
//                return new ModelAndView(redirect);
//            }
//        }
//
//        Integer wechatUserId = ((WechatUserOutput)result.getData()).getWechatUserId();
//
//        String  jsApiTicket = config.getApiConfig().getJsApiTicket();
//
//        redirect += "?" + redirectToutiaoParamUserId + "=" + String.valueOf(wechatUserId);
//        redirect += "&" + redirectToutiaoParamAppId + "=" + config.getApiConfig().getAppid();
//        redirect += "&" + redirectToutiaoParamJsapiTicket + "=" + jsApiTicket
//        redirect += "&" + redirectToutiaoParamOpenId + "=" + openid;

        return new ModelAndView(redirect);
    }

    @RequestMapping("/redirectInfo")
    public ModelAndView handleRedirectInfo(@RequestParam("code") String code) {
        String redirect = "redirect:" + redirectInfoUrl;

        OauthAPI oauthAPI = new OauthAPI(config.getApiConfig());
        OauthGetTokenResponse response = oauthAPI.getToken(code);

//
//        String openid = response.getOpenid();
//        if (StringUtils.isEmpty(openid)) {
//            return new ModelAndView(redirect);
//        }
//
//        String headImgUrl = null;
//        ApiResult result = dubboService.getWechatUser(openid);
//        if (ResultCode.ERROR.getCode() == result.getCode()) {
//            UserAPI userAPI = new UserAPI(config.getApiConfig());
//            GetUserInfoResponse userInfo = userAPI.getUserInfo(openid);
//            if (StringUtils.isEmpty(userInfo.getOpenid())) {
//                return new ModelAndView(redirect);
//            }
//
//            result = dubboService.createWechatUser(userInfo);
//            if (ResultCode.ERROR.getCode() == result.getCode()) {
//                return new ModelAndView(redirect);
//            }
//
//            headImgUrl = userInfo.getHeadimgurl();
//        } else {
//            WechatUserOutput data = (WechatUserOutput) result.getData();
//            headImgUrl = data.getHeadimgurl();
//        }
//
//        String userinfo = Util.encodeWechatUserInfo(openid, headImgUrl);
//        if (StringUtils.isEmpty(userinfo)) {
//            return new ModelAndView(redirect);
//        }
//
//        redirect += "?" + redirectBindParamUserInfo + "=" + userinfo;

        return new ModelAndView(redirect);
    }
}