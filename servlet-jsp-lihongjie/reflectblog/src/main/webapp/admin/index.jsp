<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>轻博客后台页面框架</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <frameset rows="20%,80%" noresize="noresize">
  	<frame src="/myblog/admin/top.jsp" frameborder="0" noresize="noresize"> 
  		<frameset cols="20%, 80%" noresize="noresize">
     		<frame src="/myblog/admin/left.jsp"  frameborder="0">
     		<frameset rows="90%,10%" noresize="noresize">
     		<frame src="" frameborder="0" name="mainFrame">
     		<frame src="/myblog/admin/footer.jsp" frameborder="0">
     		</frameset>
		</frameset>
 	<frame>
 </frameset>
  <body>
  </body>

</html>
