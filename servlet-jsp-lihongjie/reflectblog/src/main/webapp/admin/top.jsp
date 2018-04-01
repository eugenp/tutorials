<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台上部页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
		#top {
			text-align:center;
			margin:0px auto;
			height:50px;
			font-family:Verdana,宋体, Geneva, Arial, Helvetica, sans-serif;
			border:1px solid;
		}
		#ss {
			height:30px;
			border:1px solid ;
		}
		#left {
			float:left;
			text-align:center;
		}
		#right {
			float:right;
			text-align:center;
		}
	</style>
  </head>
  <body>
  	<div id="top">
  		<h2>MyBlog博客后台管理</h2>
  	</div>
  	<%String value = "";
		//获取Cookie 的信息
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie :cookies) { //或者for循环 ： for(int i=0;i<cookies.length;i++)
				String iname = cookie.getName();
				if("user".equals(iname)) {
					value = cookie.getValue();
					break;
				}
			}
		}  %>
  	<div id="ss">
  		<span id="left">查看博客</span>
  		<span id="right"><%=value %>&nbsp;已登录
  		</span>
  		
  	</div>
  </body>
</html>
