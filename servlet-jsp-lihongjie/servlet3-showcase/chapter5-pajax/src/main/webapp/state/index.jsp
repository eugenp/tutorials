<%--
具体api介绍请参考
https://developer.mozilla.org/en-US/docs/Web/Guide/DOM/Manipulating_the_browser_history?redirectlocale=en-US&redirectslug=DOM%2FManipulating_the_browser_history
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>无刷新更新页面片段</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<div id="header">
    页面头部
</div>

<div id="container">
    <a href="page1.jsp">第一页</a>
    <a href="page2.jsp">第二页</a>
    <a href="page3.jsp">第三页</a>
</div>

<div id="footer">
    页面尾部
</div>

<script src="${pageContext.request.contextPath}/static/jquery.1.10.2.js"></script>
<script>
    $(function () {
        //1、如果更改历史记录，则会触发popstate事件
        //2、如果激活的历史记录是pushState或replaceState创建的，history.state是其状态数据的一个副本
        $(window).on("popstate", function() {
            if(history.state) {
                var currentState = history.state;
                console.info(currentState.url);
                console.info(currentState.msg);
            }
        });
        var initHistoryState = function(links) {
            links.on("click", function () {
                if (history.pushState) { //支持html5的维护历史记录栈
                    var link = $(this);
                    var url = link.attr("href");
                    var title = link.text();
                    $.get(url).success(function(data) {
                        $("#container").html(data);
                        var stateData = {
                            url : url,
                            msg : 'hello'
                        };
                        //将新的地址压入历史记录栈，而且地址栏地址也发生改变
                        //但该页面没有刷新，而是通过ajax取得一个html片段更新的
                        history.pushState(stateData, title, url);
                        //再初始化页面按钮
                        setTimeout(function() {
                            initHistoryState($("#container a"));
                        }, 0);
                    });
                } else {
                    alert("您的浏览器不支持html5的history.pushState");
                }
                return false;
            });
        };
        initHistoryState($("a"));
    });
</script>
</body>
</html>