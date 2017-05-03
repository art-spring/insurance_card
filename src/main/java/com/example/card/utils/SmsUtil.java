package com.example.card.utils;

import com.example.card.model.VerificationCode;
import com.example.card.enums.SmsCodeCheckResult;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * Created by racoon on 2017/4/26.
 */
public class SmsUtil {



    private static Map<String, List<VerificationCode>> cacheMap = new HashMap<String, List<VerificationCode>>();

    public static SmsCodeCheckResult checkCode(String phone, String code, long overTime) {
        VerificationCode temp = SmsUtil.getLastCode(phone);
        if (temp != null) {
            long now = new Date().getTime();
            long last = temp.getDate().getTime();
            long dis = now - last;
            if (dis > overTime) {
                return SmsCodeCheckResult.TIME_OUT;
            } else {
                if (Integer.parseInt(code) != temp.getCode()) {
                    return SmsCodeCheckResult.NOT_MATCH;
                }
            }
        } else {
            return SmsCodeCheckResult.REGET;
        }
        return SmsCodeCheckResult.MATCH;
    }

    public static void saveCodeToCache(String phone, int code) {
        if (SmsUtil.cacheMap.containsKey(phone)) {
            SmsUtil.cacheMap.get(phone).add(SmsUtil.createBean(phone, code));
        } else {
            List<VerificationCode> list = new ArrayList<>();
            list.add(SmsUtil.createBean(phone, code));
            SmsUtil.cacheMap.put(phone, list);
        }
    }

    public static VerificationCode getLastCode(String phone) {
        VerificationCode temp = null;
        List<VerificationCode> list = SmsUtil.cacheMap.get(phone);
        if (null != list && list.size() > 0) {
            temp = list.get(list.size() - 1);
        }
        return temp;
    }

    private static VerificationCode createBean(String phone, int code) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setPhone(phone);
        verificationCode.setDate(new Date());
        return verificationCode;
    }

    /**
     * 通过模板发送短信(不推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     */
    public static String tplSendSms(String uriTplSend, String encoding, String apikey, long tpl_id, String tpl_value, String mobile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(uriTplSend, encoding, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, String encoding, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, encoding));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
