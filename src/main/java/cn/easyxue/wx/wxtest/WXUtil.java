/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.easyxue.wx.wxtest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HttpClient client = new HttpClient();
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appId, appsecret, code);
        HttpMethod get = new GetMethod(url);
        try {
            client.executeMethod(get);
            return get.getResponseBodyAsString();
        } catch (IOException ex) {
            log.error("Can't get accessToken!", ex);
            return ex.getLocalizedMessage();
        }
        
    }

}
