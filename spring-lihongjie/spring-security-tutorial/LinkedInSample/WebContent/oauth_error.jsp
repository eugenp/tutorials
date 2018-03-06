<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.AuthenticationException"%>
<%@ page
	import="org.springframework.security.oauth.consumer.OAuthConsumerProcessingFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OAuth示例应用</title>
</head>
<body>
<div id="container">

<div id="content"><c:if
	test="${!empty sessionScope.OAUTH_FAILURE_KEY}">
	<h1>OAuth认证过程出错！</h1>

	<p>
	<%
		((AuthenticationException) session
					.getAttribute(OAuthConsumerProcessingFilter.OAUTH_FAILURE_KEY))
					.printStackTrace();
	%>
	</p>

	<p class="error">OAuth认证过程中出现错误，错误消息是： <%=((AuthenticationException) session
						.getAttribute(OAuthConsumerProcessingFilter.OAUTH_FAILURE_KEY))
						.getMessage()%></p>
</c:if> <c:remove scope="session" var="OAUTH_FAILURE_KEY" /></div>
</div>
</body>
</html>