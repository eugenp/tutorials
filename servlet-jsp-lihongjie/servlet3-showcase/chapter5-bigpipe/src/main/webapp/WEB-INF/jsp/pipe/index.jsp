<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //chunk 1kb渲染要求，即有些浏览器在渲染时要求第一段chunk 1kb时才渲染
    //经测试
    //如果内容含义如html可显示tag，Opera12 Chrome27 firefox4直接可以渲染，不需要1kb
    //如果是IE（8，测试的版本），还是需要1KB
    //网上搜索说Safari也是，没有测试
    for(int i = 0; i < 1024; i++) {
        out.write(' ');
    }
%>
<html>
<head>
    <title></title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/pipe.css">
</head>
<body>

    <div>首页主要内容</div>

    <%
        out.flush();
        Thread.sleep(5000);
    %>


    <div id="part1">
        这是第一段内容
    </div>

    <%
        out.flush();
        Thread.sleep(5000);
    %>

    <div id="part2">
        这是第二段内容
    </div>

    <%
        out.flush();
        Thread.sleep(5000);
    %>

    <div id="part3">
        这是第三段内容
    </div>


</body>
</html>