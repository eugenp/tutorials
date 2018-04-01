<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="title" value="第一页"/>
<%@include file="header.jsp"%>
<div id="container">
    <a href="${pageContext.request.contextPath}/test/page1" class="active">第一页</a>
    <a href="${pageContext.request.contextPath}/test/page2">第二页</a>
    <a href="${pageContext.request.contextPath}/test/page3">第三页</a>
</div>
<%@include file="footer.jsp"%>
