package com.jcwenhua.card.wechat.tool;

/**
 * Created by racoon on 2017/4/17.
 */

import com.jcwenhua.card.wechat.config.Config;
import com.github.sd4324530.fastweixin.api.JsAPI;
import com.github.sd4324530.fastweixin.api.MaterialAPI;
import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.api.response.GetSignatureResponse;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse;
import com.github.sd4324530.fastweixin.api.response.UploadMaterialResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
public class WechatTool {

    private static final Logger LOG = LoggerFactory.getLogger(WechatTool.class);

    @Autowired
    private Config config;

    @Value("${wechat.menu.url.joinin}")
    private String wechatMenuUrlJoinin;

    @Value("${wechat.menu.url.info}")
    private String wechatMenuUrlInfo;

    @Value("${wechat.menu.url.activecard}")
    private String wechatMenuUrlActivecard;

    @Value("${wechat.menu.url.agent}")
    private String wechatMenuUrlAgent;

    private void init() {
        DefaultConfigChangeHandler configChangeHandle = new DefaultConfigChangeHandler();
        config.getApiConfig().addHandle(configChangeHandle);
    }

    private MenuButton createMenuItem(MenuType type, String name, String key, String url) {
        MenuButton menuButton = new MenuButton();
        menuButton.setType(type);
        menuButton.setName(name);
        menuButton.setKey(key);
        menuButton.setUrl(url);
        return menuButton;
    }

    private MenuButton createMenuItem4Media(MenuType type, String name, String key, String mediaId) {
        MenuButton menuButton = new MenuButton();
        menuButton.setType(type);
        menuButton.setName(name);
        menuButton.setKey(key);
        menuButton.setMediaId(mediaId);
        return menuButton;
    }

    /**
     * 创建菜单
     */
    private void createMenu() {
        MenuAPI menuAPI = new MenuAPI(config.getApiConfig());

        //先删除之前的菜单
        menuAPI.deleteMenu();
        Menu menu = new Menu();
        //准备一级主菜单
        MenuButton menu1 = createMenuItem(MenuType.CLICK, "生活服务", "1", null);
        MenuButton menu1_sub1 = createMenuItem4Media(MenuType.VIEW, "餐饮服务", "1-1", wechatMenuUrlJoinin);
        MenuButton menu1_sub2 = createMenuItem4Media(MenuType.VIEW, "汽车服务", "1-2", "https://www.baidu.com");
        MenuButton menu1_sub3 = createMenuItem4Media(MenuType.VIEW, "体检中心", "1-3", "https://www.baidu.com");
        menu1.setSubButton(Arrays.asList(menu1_sub1,menu1_sub2,menu1_sub3));

        MenuButton menu2 = createMenuItem(MenuType.CLICK, "会员服务", "2", null);
        MenuButton menu2_sub1 = createMenuItem(MenuType.VIEW, "个人中心", "2-1", wechatMenuUrlInfo);
        MenuButton menu2_sub2 = createMenuItem(MenuType.VIEW, "激活卡片", "2-2", wechatMenuUrlActivecard);
        menu2.setSubButton(Arrays.asList(menu2_sub1,menu2_sub2));

        MenuButton menu3 = createMenuItem(MenuType.VIEW, "合作商家","3", wechatMenuUrlAgent);

        //将主菜单加入请求对象
        menu.setButton(Arrays.asList(menu1,menu2,menu3));
        LOG.debug(menu.toJsonString());
        //创建菜单
        ResultType resultType = menuAPI.createMenu(menu);
        LOG.debug(resultType.toString());
    }

    public void testJsApiSign(){
        JsAPI jsAPI = new JsAPI(config.getApiConfig());
        GetSignatureResponse response = jsAPI.getSignature("http://mp.weixin.qq.com");
        LOG.debug(response.toJsonString());
    }

    public void uploadImageMaterial(){
        MaterialAPI materialAPI = new MaterialAPI(config.getApiConfig());
        UploadMaterialResponse response = materialAPI.uploadMaterialFile(new File("D:\\2.2.png"));
        System.out.println(response.getMediaId());
    }

    public void getCurrentUserList() {
        UserAPI userAPI = new UserAPI(config.getApiConfig());
        GetUsersResponse response = userAPI.getUsers(null);
        GetUsersResponse.Openid data = response.getData();

        String prefix = "INSERT INTO wechat_user (subscribe , openid , nickname , sex , city , " +
                "country , province ,language , headimgurl , subscribe_time , " +
                "unionid , remark , groupid, create_time , update_time ) VALUES (";
        String suffix = "CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
        for (String openid: data.getOpenid()) {
            // System.out.println(openid);
            GetUserInfoResponse userInfoResponse = userAPI.getUserInfo(openid);
            String sql = prefix;
            sql += userInfoResponse.getSubscribe() + ",";
            sql += "'" + openid + "',";
            sql += "'" + userInfoResponse.getNickname() + "',";
            sql += userInfoResponse.getSex() + ",";
            sql += "'" + userInfoResponse.getCity() + "',";

            sql += "'" + userInfoResponse.getCountry() + "',";
            sql += "'" + userInfoResponse.getProvince() + "',";
            sql += "'" + userInfoResponse.getLanguage() + "',";
            sql += "'" + userInfoResponse.getHeadimgurl() + "',";
            sql += userInfoResponse.getSubscribeTime() + ",";

            sql += "'" + userInfoResponse.getUnionid() + "',";
            sql += "'" + userInfoResponse.getRemark() + "',";
            sql += userInfoResponse.getGroupid() + ",";

            sql += suffix;

            System.out.println(sql);
        }

        // #{subscribe} , #{openid} , #{nickname} , #{sex} , #{city} , #{country} , #{province} ,#{language} , #{headimgurl} , #{subscribeTime} , #{unionid} , #{remark} , #{groupid} ,
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContent.xml");
        WechatTool tool = ctx.getBean(WechatTool.class);
        tool.init();
        tool.createMenu();
//        tool.uploadImageMaterial();
//        tool.getCurrentUserList();
    }
}
