<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
    <style type="text/css">
        body {
            vertical-align: top;
        }
        #user-list {
            float: left;
            width: 150px;
            height: 200px;
            overflow: auto;
            padding: 10px;
            border: 1px #ccc solid;
            margin: 0 10px 50px 0;
        }
        #user-list ul {
            padding-left: 0px;
            margin: 0;
            list-style-type:none;
        }
        #msg-box {
            width: 500px;
            height: 200px;
            overflow: auto;
            border: 1px #ccc solid;
            margin-bottom: 50px;
            padding: 10px;
        }
        .msg {
            padding: 2px 0;
        }
    </style>
</head>
<body>
    <div>
        <div style="float: left;">在线用户列表</div>
        <div style="float: left;width: 85px;height: 1px;"></div>
        <div>消息列表</div>
    </div>
    <div id="user-list">
        <ul>
            <%
                Set<String> loginUsers = (Set<String>) request.getAttribute("loginUsers");
                for(Object loginUser : loginUsers) {
                    pageContext.setAttribute("loginUser", loginUser);
            %>
            <li id="user-${loginUser}">${loginUser}</li>
            <%
                }
            %>
        </ul>
    </div>

    <div id="msg-box"></div>

    我自己：<span id="username">${param.username}</span><br/>
    发送给：<input type='text' id="receiver">(不填代表所有人)<br/>
    消&nbsp;&nbsp;息：<textarea id="msg" cols="20" rows="5"></textarea><br/>
    <input id="send-btn" type="button" value="发送"/>

    <script src="${pageContext.request.contextPath}/static/jquery.1.10.2.js"></script>
    <script type="text/javascript">
        $(function() {
            var msgBox = $("#msg-box");
            var pollUrl = "${pageContext.request.contextPath}/chat?command=poll&username=${param.username}";
            var chat = {
                login : function(username) {
                    var userLi = $("#user-" + username);
                    if(!userLi.length) {
                        userLi = $("<li></li>").attr("id", "user-" + username).text(username);
                        $("#user-list > ul").append(userLi);
                        chat.publish(null, username + "上线了");
                    }
                },
                logout : function(username) {
                    var userLi = $("#user-" + username);
                    if(userLi.length) {
                        userLi.remove();
                        chat.publish(null, username + "下线了");
                    }
                },
                /**
                *
                * @param username 如果是登录/退出 username请设置为null
                * @param msg
                 */
                publish : function(username, msg) {
                    if(username) {
                        chat.login(username);
                    }

                    var msgList = msgBox.find(".msg");
                    if(msgList.length >= 50) {//如果消息超过50个，删除老的
                        for(var i = msgList.length - 50 - 1; i >= 0; i--) {
                            $(msgList.get(i)).remove();
                        }
                    }
                    msgBox.append($("<div></div>").addClass("msg").html(msg));
                    msgBox.animate({scrollTop:  msgBox.scrollTop() + msgBox.height()}, 300);
                },
                callback : function(data) {
                    if(!data) {
                        return;
                    }
                    data = $.parseJSON(data);
                    switch (data.type) {
                        case "login" :
                            chat.login(data.username);
                            break;
                        case "logout" :
                            chat.logout(data.username);
                            break;
                        case "msg" :
                            chat.publish(data.username, data.username + ":" + data.msg);
                            break;
                        default :
                        //ignore
                    }

                },
                longPolling : function(url, callback) {
                    $.ajax({
                        url: url,
                        async: true,
                        cache: false,
                        global: false,
                        timeout: 30 * 1000,
                        success: function (data,status,request) {
                            callback(request.responseText);
                            data = null;
                            status = null;
                            request = null;
                            setTimeout(
                                    function() {chat.longPolling(pollUrl, callback);},
                                    10
                            );
                        },
                        error: function (xmlHR, textStatus, errorThrown) {
                            xmlHR = null;
                            textStatus = null;
                            errorThrown = null;

                            setTimeout(
                                    function() {chat.longPolling(pollUrl, callback);},
                                    5 * 1000
                            );
                        }
                    });
                },
                initialize : function() {
                    chat.longPolling(pollUrl, chat.callback);
                }
            };

            $("#send-btn").click(function() {
                var receiver = $("#receiver").val();
                var msg = $("#msg").val();
                chat.publish(null, "我:" + msg);
                $.get("${pageContext.request.contextPath}/chat?command=send&receiver="+receiver + "&msg=" + msg);
            });

            chat.initialize();

        });
    </script>


</body>
</html>