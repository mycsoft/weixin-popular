<%-- 
    Document   : login
    Created on : 2017-3-7, 16:59:19
    Author     : MaYichao
--%>

<%@page import="weixin.popular.bean.sns.SnsToken"%>
<%@page import="cn.easyxue.wx.util.WXConfig"%>
<%@page import="cn.easyxue.wx.util.WXClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String code = request.getParameter("code");
//    String appId = "wx2f07b03f14b9bfcf";
//    String appsecret = "7a004e0e42e8f444bc291e4d4a8c188b";
    //KYJODMbDfh3EAUZFahvmikkdmLeP9V9TbOadygaeDQCke7BXnGuPHCjPykU-Gh29Kgvg2PAaKBUMsLWjoDcOQhfXhAXDCwXd12j7_u1WFsUZCKdAAAQTB
//    HttpClient client = new HttpClient();
//    String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appid, appsecret, code);
//    HttpMethod get = new GetMethod(url);
//    client.executeMethod(get);
//    String result = WXUtil.code2accessToken(appId, appsecret, code);
    WXClient wxc = WXClient.create(request);
    SnsToken token = wxc.createSnsToken(code);
//    String accessToken = wxc.getAccessToken();
    if (token != null) {
        response.sendRedirect("hello.jsp");
    }
%>

<html>
    <head>
        <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap-theme.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1>登录页面</h1>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">测试结果</h3>
                </div>
                <div id="msg"class="panel-body">
                    <p>正在更新code <%= code%></p>
                    <p class="text-primary">更新结果: <%--= accessToken --%></p>

                </div>

            </div>

        </div>

    </body>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

    <script>
        var code = '<%=code%>';
        var appid = "wx2f07b03f14b9bfcf";
        var appsecret = "7a004e0e42e8f444bc291e4d4a8c188b";
        var url = 'https://api.weixin.qq.com/sns/oauth2/access_token?appid=' + appid + '&secret=' + appsecret + '&code=' + code + '&grant_type=authorization_code';




        function println(str, css) {
            var $p = $('<p>');
            if (css === undefined) {
                css = 'text-primary';
            }
            $p.addClass(css).text(str);
            $('#msg').append($p);
        }
        function error(str) {
            println(str, 'text-danger')
        }
//        $('#msg').append("<p>开始...</p>");
        println('开始...');
//        $.ajax(
//                url,
//                {
//                    data: {
//                        appid: appid,
//                        appsecret: appsecret,
//                        code: code,
//                        grant_type: 'authorization_code'
//                    },
//                    success: function (json) {
//                        $('#msg').append(JSON.stringify(json));
//                    },
//                    error: function (event, textStatus, errorThrown) {
//                        error('请求错误!');
//                        error(errorThrown);
//                    }
//                }
//        );
//        wx.config({
//            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
//            appId: appid, // 必填，公众号的唯一标识
//            timestamp: '20170307', // 必填，生成签名的时间戳
//            nonceStr: '', // 必填，生成签名的随机串
//            signature: '', // 必填，签名，见附录1
//            jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//        });
//        $.ajaxError(function (event) {
//            error('请求错误!');
//            error(event);
//        });
//        $.ajaxSetup({
//            error: function (event) {
//                error('请求错误!');
//                error(event);
//            }
//        });

    </script>
</html>
