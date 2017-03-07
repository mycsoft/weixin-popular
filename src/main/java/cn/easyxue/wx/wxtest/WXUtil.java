/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.easyxue.wx.wxtest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author MaYichao
 */
public class WXUtil {

    private static Log log = LogFactory.getLog(WXUtil.class);

    /**
     * code 换成永久令牌
     *
     * @param appId
     * @param appsecret
     * @param code
     * @return
     */
    public static String code2accessToken(String appId, String appsecret, String code) {
//        HttpClient client = new HttpClient();
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appId, appsecret, code);
//        HttpMethod get = new GetMethod(url);
        try {
//            client.executeMethod(get);
            return httpGetJSON(url).getString("access_token");
//            return get.getResponseBodyAsString();
        } catch (IOException ex) {
            log.error("Can't get accessToken!", ex);
            return ex.getLocalizedMessage();
        }

    }

    public static String getAccessToken(HttpSession session) {
        return (String) session.getAttribute("access_token");
    }

    /**
     *
     * @param url
     * @param param
     * @return
     */
    private static JSONObject httpGetJSON(String url, Object... param) throws IOException {
        String s = httpGet(url, param);
        return JSON.parseObject(s);
    }

    /**
     *
     * @param url
     * @param param
     * @return
     */
    private static String httpGet(String url, Object... param) throws IOException {
        HttpClient client = new HttpClient();
        String url2 = String.format(url, param);
        HttpMethod get = new GetMethod(url2);
        client.executeMethod(get);
        return get.getResponseBodyAsString();
    }

    /**
     * 微信客户端.
     */
    public static class WXClient {

        private String appId;
        private String appsecret;
        private String accessToken;
        private HttpServletRequest request;

        public WXClient(HttpServletRequest request, String appId, String appsecret) {

            assert request != null && appId != null && appsecret != null;

            this.request = request;
            this.appId = appId;
            this.appsecret = appsecret;
        }

        public String getAccessToken() {
            if (accessToken == null) {
                //从会话里找.
                accessToken = (String) request.getSession().getAttribute("access_token");

                if (accessToken == null) {
                    //会话里没有,就向微信服务器要.
                    accessToken = requestNewAccessToken();
                    request.getSession().setAttribute("access_token", accessToken);
                }

                assert accessToken == null : "微信怎么没给个令牌呢?";
            }
            return accessToken;
        }

        /**
         * 根据临时码取得令牌.
         *
         * @param code
         * @return
         */
        public String getAccessTokenByCode(String code) {
            if (accessToken == null) {
                //从会话里找.
                accessToken = (String) request.getSession().getAttribute("access_token");

                if (accessToken == null) {
                    //会话里没有,就向微信服务器要.
                    accessToken = code2accessToken(code);
                    request.getSession().setAttribute("access_token", accessToken);
                }

                assert accessToken == null : "微信怎么没给个令牌呢?";
            }
            return accessToken;
        }

        public String code2accessToken(String code) {
            return WXUtil.code2accessToken(appId, appsecret, code);

        }

        /**
         * 向微信申请令牌.
         *
         * @return
         */
        private String requestNewAccessToken() {
            try {
                JSONObject result = httpGetJSON("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appsecret);
                return result.getString("ACCESS_TOKEN");
            } catch (IOException ex) {
                log.error("Can't get accessToken!", ex);
                return ex.getLocalizedMessage();
            }

        }

//        /**
//         *
//         * @param url
//         * @param param
//         * @return
//         */
//        private String httpGet(String url, Object... param) throws IOException {
//            HttpClient client = new HttpClient();
//            String url2 = String.format(url, param);
//            HttpMethod get = new GetMethod(url2);
//            client.executeMethod(get);
//            return get.getResponseBodyAsString();
//        }
    }

}
