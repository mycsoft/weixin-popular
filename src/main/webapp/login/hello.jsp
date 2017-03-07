<%-- 
    Document   : hello
    Created on : 2017-3-7, 20:13:03
    Author     : MaYichao
--%>

<%@page import="cn.easyxue.wx.wxtest.WXUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String appId = "wx2f07b03f14b9bfcf";
    String appsecret = "7a004e0e42e8f444bc291e4d4a8c188b";
    WXUtil.WXClient wxc = new WXUtil.WXClient(request, appId, appsecret);
    String accessToken = wxc.getAccessToken();

%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap-theme.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>你好</title>
    </head>
    <body>
        <h1>Token=<%=accessToken%></h1>
        
        
        
        <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
        <script src="http://apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    </body>
</html>
