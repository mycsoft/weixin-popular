/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.easyxue.wx.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weixin.popular.api.TokenAPI;
import weixin.popular.support.TokenManager;

/**
 * 微信客户端. 本类是调用微信服务的统一入口.
 *
 * @author MaYichao
 * @see WXUtil.WXClient
 */
public class WXClient {

    private static Log log = LogFactory.getLog(WXClient.class);

//    private String appId;
//    private String appsecret;
    private String accessToken;
    private HttpServletRequest request;

//    private TokenManager tokenManager = new TokenManager();
    private WXConfig wxc;

    @Deprecated
    public WXClient(HttpServletRequest request, String appId, String appsecret) {
        this(request, WXConfig.createWXConfig(appId, appsecret));

//        assert request != null && appId != null && appsecret != null;
//        this.request = request;
//        this.appId = appId;
//        this.appsecret = appsecret;
    }

    public WXClient(HttpServletRequest request, WXConfig config) {

        assert request != null && config != null;

        this.request = request;
        this.wxc = config;
        TokenManager.init(wxc.getAppId(), wxc.getAppsecret());

    }

    public String getAccessToken() {
        return TokenManager.getDefaultToken();
//        if (accessToken == null) {
//            //从会话里找.
//            accessToken = (String) request.getSession().getAttribute("access_token");
//
//            if (accessToken == null) {
//                //会话里没有,就向微信服务器要.
//                accessToken = requestNewAccessToken();
//                request.getSession().setAttribute("access_token", accessToken);
//            }
//
//            assert accessToken == null : "微信怎么没给个令牌呢?";
//        }
//        return accessToken;
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

    @Deprecated
    public String code2accessToken(String code) {
//        return WXUtil.code2accessToken(wxc.getAppId(), wxc.getAppsecret(), code);
        return code;

    }

//    /**
//     * 向微信申请令牌.
//     *
//     * @return
//     */
//    private String requestNewAccessToken() {
////        try {
////            JSONObject result = WXUtil.httpGetJSON("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appsecret);
////            return result.getString("ACCESS_TOKEN");
////        } catch (IOException ex) {
////            log.error("Can't get accessToken!", ex);
////            return ex.getLocalizedMessage();
////        }
//        return TokenAPI.token(wxc.getAppId(), wxc.getAppsecret()).getAccess_token();
//    }
}
