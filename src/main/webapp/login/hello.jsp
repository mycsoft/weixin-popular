<%-- 
    Document   : hello
    Created on : 2017-3-7, 20:13:03
    Author     : MaYichao
--%>

<%@page import="weixin.popular.bean.token.Token"%>
<%@page import="weixin.popular.bean.user.User"%>
<%@page import="weixin.popular.bean.sns.SnsToken"%>
<%@page import="cn.easyxue.wx.util.WXConfig"%>
<%@page import="cn.easyxue.wx.util.WXClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    WXClient wxc = WXClient.create(request);
    SnsToken sToken = wxc.getSnsToken();
    String token = wxc.getAccessToken();
//    String jsTicket = wxc.getJsTicket();
//    String wxConfigJson = wxc.createJsConfigJSON(request);
    
    User user = wxc.getUserInfo();

%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.1/weui.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>个人首页</title>
    </head>
    <body>
        <div class="container">
            <div class="page-header">
                <h1><span id="name">尊敬的用户</span>,<small>你好!</small></h1>
            </div>

        </div>

        <dl class="dl-horizontal bg-primary">
            <dt>OpenID</dt>
            <dd><%= sToken.getOpenid()%></dd>

            <dt>Token</dt>
            <dd><%= token%></dd>
            <dt>Ticket</dt>
            <dd><%--= jsTicket--%></dd>
        </dl>
        <dl class="dl-horizontal">
            <dt>名称</dt>
            <dd id="name"><%= user.getNickname() %></dd>
            <dt>头像</dt>
            <dd><img id="headimage" class="img-round" src="<%= user.getHeadimgurl() %>"/></dd>

            <dt>性别</dt>
            <dd id="sex"><%= user.getSex() %></dd>


            <dt>unionid</dt>
            <dd id="unionid"><%= user.getUnionid() %></dd>

        </dl>

        <div class="foot">
            <div id="msg">

            </div>
        </div>




        <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
        <script src="http://apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
        <script>
//            wx.config({
//                debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
//                appId: '<%= wxc.getWXConfig().getAppId()%>', // 必填，公众号的唯一标识
//                timestamp: 20170301, // 必填，生成签名的时间戳
//                nonceStr: '123', // 必填，生成签名的随机串
//                signature: '', // 必填，签名，见附录1
//                jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//            });
//            wx.config(<%--= wxConfigJson--%>);
//            wx.ready(function () {
//
//                // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
//
//                println('微信JS SDK Config接口注入权限验证配置成功!');
//
//                wx.checkJsApi({
//                    jsApiList: ['chooseImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
//                    success: function (res) {
//                        // 以键值对的形式返回，可用的api值true，不可用为false
//                        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
//                    }
//                });

//                //取得用户信息
//                $.getJSON(
//                        'https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN',
//                        {
//                            access_token: '<%= sToken.getAccess_token()%>',
//                            openid: '<%= sToken.getOpenid()%>',
//                            lang: 'zh_CN'
//                        }, function (json) {
//                    if (json.errcode != undefined) {
//                        alert('Error:' + json.errmsg);
//                    }
//                    $('#name').text(json.nickname);
//                    $('#headimage').attr('src',json.headimgurl);
//                });

//            });



//            wx.error(function (res) {
//                // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
//
//            });


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
        </script>
    </body>
</html>
