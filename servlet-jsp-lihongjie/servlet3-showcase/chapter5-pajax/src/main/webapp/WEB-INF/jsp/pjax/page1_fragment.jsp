<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a data-pjax href="${pageContext.request.contextPath}/pjax/page1" class="active">第一页</a>
<a data-pjax href="${pageContext.request.contextPath}/pjax/page2">第二页</a>
<a data-pjax href="${pageContext.request.contextPath}/pjax/page3">第三页</a>
<a href="${pageContext.request.contextPath}/pjax/page4">第四页(不使用pjax)</a>