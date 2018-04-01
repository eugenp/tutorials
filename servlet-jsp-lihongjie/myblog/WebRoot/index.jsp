<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个人博客前台主页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<style ></style>
  </head>
  
  <body background="/myblog/admin/images/mainbg.jpg">
 	<div id="body">
 		<div id="head">
  		 <div id="banner">
  		 <h1>在线学IT技术</h1>
  		 </div>
  		</div>
  		<div id="main">
  			<div id="left">
  				<h3>2013年5月</h3>
  				<br>
  				<h4><a href="#">软件一班</a></h4>
  				<h5>软件一班</h5>	
  				<p>
  				<h4><a href="#">点击查看原文</a></h4>
 				<hr>
  			</div>
  			<div id="right">
  				<div id="one">
  					<h4>博客简介</h4>
  					<hr>
  					<a href="#">.....</a>
  				</div>
  				<div id="two">
  					<h4>订阅博客</h4>
  					<hr>
  					<h5>订阅我的博客：</h5>
  				</div>
  				<div id="three">
  					<h4>分类</h4>
  					<hr>
  					<h5><a href="#">1</a></h5>
  					<h5><a href="#">2</a></h5>
  					<h5><a href="#">3</a></h5>
  					<h5><a href="#">4</a></h5>
  					<h5><a href="#">5</a></h5>
  				</div>
  				<div id="four">
  					<h4>最近发表</h4>
  					<hr>
  					<a href="">android安装</a></br>
  					<a href=""> java</a>
  				</div>
  				<div id="five">
  					<h4>最新评论</h4>
  					<hr>
  				</div>
  			</div>		
		</div>  
  	</div> 
  </body>
</html>
