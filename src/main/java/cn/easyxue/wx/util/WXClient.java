/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.easyxue.wx.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;
import weixin.popular.util.JsUtil;

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
    private static WXConfig wxc;

    private SnsToken snsToken;

    static {
        wxc = WXConfig.getTestWXConfig();
        TokenManager.init(wxc.getAppId(), wxc.getAppsecret());
        TicketManager.init(wxc.getAppId());
    }

    private WXClient() {
    }

    @Deprecated
    public WXClient(HttpServletRequest request, String appId, String appsecret) {
        this(request, WXConfig.createWXConfig(appId, appsecret));

//        assert request != null && appId != null && appsecret != null;
//        this.request = request;
//        this.appId = appId;
//        this.appsecret = appsecret;
    }

    /**
     *
     * @param request
     * @param config
     * @deprecated 应该改为工作方法,可以从会话中返回默认对象,减少对象创建数量.
     */
    @Deprecated
    public WXClient(HttpServletRequest request, WXConfig config) {

        assert request != null && config != null;

        this.request = request;
//        this.wxc = config;
//        TokenManager.init(wxc.getAppId(), wxc.getAppsecret());
//        TicketManager.init(wxc.getAppId());

    }

    /**
     *
     * @param request
     * @param config
     */
    public static WXClient create(HttpServletRequest request) {

        assert request != null;
        WXClient wxc = new WXClient();
        wxc.request = request;
//        this.wxc = config;
//        TokenManager.init(wxc.getAppId(), wxc.getAppsecret());
//        TicketManager.init(wxc.getAppId()); 
        return wxc;
    }

    public String getAccessToken() {
        return TokenManager.getDefaultToken();
    }

    /**
     * 创建js验证的json.
     *
     * @param req
     * @param jsApi
     * @return
     */
    public String createJsConfigJSON(HttpServletRequest req, String... jsApi) {
        return JsUtil.generateConfigJson(TicketManager.getDefaultTicket(), true, wxc.getAppId(), req.getRequestURL().toString(), jsApi);
    }

    public User getUserInfo() {
        snsToken = getSnsToken();
        return UserAPI.userInfo(getAccessToken(), snsToken.getOpenid());
    }

    /**
     * 根据临时码取得令牌.
     *
     * @param code
     * @return
     */
    public SnsToken createSnsToken(String code) {
        //会话里没有,就向微信服务器要.
        snsToken = code2accessToken(code);
        request.getSession().setAttribute("snsToken", snsToken);
        return snsToken;
    }

    /**
     * 取得Sns令牌.
     *
     * @return
     */
    public SnsToken getSnsToken() {
        if (snsToken == null) {
            //从会话里找.
            snsToken = (SnsToken) request.getSession().getAttribute("snsToken");

//            if (snsToken == null) {
//                //会话里没有,就向微信服务器要.
//                snsToken = code2accessToken(code);
//                request.getSession().setAttribute("snsToken", snsToken);
//            }
            assert snsToken == null : "微信怎么没给个令牌呢?";
        }
        return snsToken;
    }

    /**
     * 根据临时码取得令牌.
     *
     * @param code
     * @return
     */
    @Deprecated
    public String getAccessTokenByCode(String code) {
        if (accessToken == null) {
            //从会话里找.
            accessToken = (String) request.getSession().getAttribute("access_token");

            if (accessToken == null) {
                //会话里没有,就向微信服务器要.
//                accessToken = code2accessToken(code);
                request.getSession().setAttribute("access_token", accessToken);
            }

            assert accessToken == null : "微信怎么没给个令牌呢?";
        }
        return accessToken;
    }

    /**
     * 取得js sdk访问权限凭据.
     *
     * @return
     */
    public String getJsTicket() {
        return TicketManager.getDefaultTicket();
    }

    private SnsToken code2accessToken(String code) {
//        return WXUtil.code2accessToken(wxc.getAppId(), wxc.getAppsecret(), code);
        return SnsAPI.oauth2AccessToken(wxc.getAppId(), wxc.getAppsecret(), code);
//        return code;

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
    /**
     * @return the wxc
     */
    public WXConfig getWXConfig() {
        return wxc;
    }

    /**
     * @param wxc the wxc to set
     */
    public void setWXConfig(WXConfig wxc) {
        this.wxc = wxc;
    }
}
