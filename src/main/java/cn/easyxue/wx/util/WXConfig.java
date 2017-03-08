/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.easyxue.wx.util;

/**
 * 微信配置对象
 *
 * @author MaYichao
 */
public class WXConfig {

    private String appId = "wx2f07b03f14b9bfcf";
    private String appsecret = "7a004e0e42e8f444bc291e4d4a8c188b";

    /**
     * 私有化创建. 本类采用构造工厂模式.
     */
    private WXConfig() {
    }

    public static WXConfig createWXConfig(String appId, String appsecret) {
        assert appId != null && appsecret != null;
        WXConfig wxc = new WXConfig();
        wxc.appId = appId;
        wxc.appsecret = appsecret;
        return wxc;
    }

    /**
     * 取得测试配置.
     *
     * @return
     */
    public static WXConfig getTestWXConfig() {
        return new WXConfig();
    }

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return the appsecret
     */
    public String getAppsecret() {
        return appsecret;
    }

    /**
     * @param appsecret the appsecret to set
     */
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

}
