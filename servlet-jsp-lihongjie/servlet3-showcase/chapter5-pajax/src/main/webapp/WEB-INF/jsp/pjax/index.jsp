<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="title" value="首页"/>
<%@include file="header.jsp"%>
<div id="container">
    <a data-pjax href="${pageContext.request.contextPath}/pjax/page1">第一页</a>
    <a data-pjax href="${pageContext.request.contextPath}/pjax/page2">第二页</a>
    <a data-pjax href="${pageContext.request.contextPath}/pjax/page3">第三页</a>
    <a href="${pageContext.request.contextPath}/pjax/page4">第四页(不使用pjax)</a>
</div>
<%@include file="footer.jsp"%>
