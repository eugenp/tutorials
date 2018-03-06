<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("ctx", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <base href="<%=basePath%>">
    <title>左侧树形菜单栏</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" language="javascript" src="/myblog/admin/js/navi.js">
	
	</script>
  </head>
  
  <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" oncontextmenu="window.event.returnValue=false" onselectstart="event.returnValue=false" ondragstart="window.event.returnValue=false">
  																		<!-- 上面的事件在左侧菜单栏生效：禁止使用右键和 复制,禁止鼠标在网页上拖动-->
    <div id="oMTData" style="height:100%;overflow:auto;overflow-x:hidden;">
    <table width="100%" cellspacing="0" cellpadding="0" border="0" style="cursor:Hand"><!-- 手型 -->
    	<!-- 文章管理 -->
    	<tr valign="top" class="navi_Category_Unselected" onClick="switchCategoryBar(this)" onMouseOver="highlightCategoryBar(this,'over')" onMouseOut="highlightCategoryBar(this,'out')">
    										<!-- 上面时添加事件 -->
    		<td width="32" align="center"> 	 			
    		<img alt="文章管理" src="/myblog/admin/images/real.gif" width="19" height="18" align="middle">
    		</td>
    		
    		<td width="110" class="navi_Category_Item">文章管理  </td>					
			<td width="24" align="center"><img src="/myblog/admin/images/titleIcon.gif" width="15" height="15" align="absMiddle"></td>
				
    	</tr>
    	<tr>
    		<td colspan="3">
    			<div style="display:"><!-- 菜单栏是否闭合，style="display:none" -->
    				<table width="100%" cellspacing="0" cellpadding="3">
    					<tr class="navi_Item_Unselected" 
    					onClick="switchCategoryBar(this)" 
    					onMouseOver="highlightCategoryBar(this,'over')" 
    					onMouseOut=" highlightCategoryBar(this,'out')">
    										<!-- 上面代码子菜单的事件 -->
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td width="24" align="center" valign="middle"><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td class="navi_ItemName"><a href="${ctx }/EntryServlet?oper=loadCategory" target="mainFrame" class="menuItem" title="创建文章">创建文章</a></td>
    						
    					</tr>
    					<tr>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td><a href="${ctx }/EntryServlet?oper=list" target="mainFrame" class="menuItem" title="文章列表">文章列表</a></td>
    					</tr>
    				</table>
    			</div>
    		</td>
    	<tr>
    	 
    	<!-- 分类管理 -->
    	<tr class="navi_Category_Unselected">
    		<td width="32" align="center"> 	 			
    		<img alt="分类管理" src="/myblog/admin/images/real.gif" width="19" height="18" align="middle">
    		</td>
    		<td width="110" class="navi_Category_Item">分类管理  </td>
    							
			<td width="24" align="center"><img src="/myblog/admin/images/titleIcon.gif" width="15" height="15" ></td>
							
    	</tr>
    	<tr>
    		<td colspan="3">
    			<div style="display:">
    				<table width="100%" cellspacing="0" cellpadding="3">
    					<tr navi_Item_Unselected>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td width="24" align="center" valign="middle"><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td class="navi_ItemName"><a href="${ctx}/admin/categoryManage/editCategory.jsp" target="mainFrame">添加分类</a></td>
    						
    					</tr>
    					<tr>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td><a href="${ctx}/CategoryServlet?oper=listByPage" target="mainFrame">分类列表</a></td>
    					</tr>
    				</table>
    			</div>
    		</td>
    	<tr> 
    	
    	<!-- 评论管理 -->  	
    	<tr class="navi_Category_Unselected">
    		<td width="32" align="center"> 	 			
    		<img alt="评论管理" src="/myblog/admin/images/real.gif" width="19" heiht="18" align="middle">
    		</td>
    		<td width="110" class="navi_Category_Item">评论管理  </td>
    		<div>					
			<td width="24" align="center"><img src="/myblog/admin/images/titleIcon.gif" width="15" height="15" align="absMiddle"></td>
			</div>					
    	</tr>
    	<tr>
    		<td colspan="3">
    			<div style="display:">
    				<table width="100%" cellspacing="0" cellpading="3">
    					<tr navi_Item_Unselected>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td width="24" align="center" valign="middle"><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td class="navi_ItemName"><a href="${ctx }/CommentServlet?oper=list" target="mainFrame">评论列表</a></td>
    						
    					</tr>
    					
    				</table>
    			</div>
    		</td>
    	<tr> 
    	<!-- 链接管理 -->
    	<tr class="navi_Category_Unselected">
    		<td width="32" align="center"> 	 			
    		<img alt="链接管理" src="/myblog/admin/images/real.gif" width="19" heiht="18" align="middle">
    		</td>
    		<td width="110" class="navi_Category_Item">链接管理  </td>
    		<div>					
			<td width="24" align="center"><img src="/myblog/admin/images/titleIcon.gif" width="15" height="15" align="absMiddle"></td>
			</div>					
    	</tr>
    	<tr>
    		<td colspan="3">
    			<div style="display:">
    				<table width="100%" cellspacing="0" cellpading="3">
    					<tr navi_Item_Unselected>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td width="24" align="center" valign="middle"><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td class="navi_ItemName"><a href="admin/linkManage/addLink.jsp" target="mainFrame">添加友情链接</a></td>
    						
    					</tr>
    					<tr>
    						<td width="3" bgcolor="#d3eb9a"><br></td>
    						<td><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td><a href="${ctx }/LinkServlet?oper=list" target="mainFrame">友情链接列表</a></td>
    					</tr>
    				</table>
    			</div>
    		</td>
    	<tr>
    	<!-- 系统管理 -->
    	<tr class="navi_Category_Unselected">
    		<td width="32" align="center"> 	 			
    		<img alt="系统管理" src="/myblog/admin/images/real.gif" width="19" heiht="18" align="middle">
    		</td>
    		<td width="110" class="navi_Category_Item">系统管理  </td>
    		<div>					
			<td width="24" align="center"><img src="/myblog/admin/images/titleIcon.gif" width="15" height="15" align="absMiddle"></td>
			</div>					
    	</tr>
    	<tr>
    		<td colspan="3">
    			<div style="display:">
    				<table width="100%" cellspacing="0" cellpading="3">
    					<tr navi_Item_Unselected>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td width="24" align="center" valign="middle"><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td class="navi_ItemName"><a href="admin/systemManage/alterSystem.jsp" target="mainFrame">博客系统设定</a></td>
    						
    					</tr>
    					<tr>
    						<td width="3" bgcolor="#d3eb9a">&nbsp;</td>
    						<td><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td><a href="admin/systemManage/alterPassword.jsp" target="mainFrame">修改用户密码</a></td>
    					</tr>
    					<tr navi_Item_Unselected>
    						<td width="3" bgcolor="#d3eb9a"></td>
    						<td width="24" align="center" valign="middle"><img src="/myblog/admin/images/button_right.gif" align="middle"></td>
    						<td class="navi_ItemName"><a href="admin/login.jsp" target="_top">退出系统</a></td>
    						
    					</tr>
    				</table>
    			</div>
    		</td>
    	<tr>
    	
    </table>
    </div>
    
  </body>
</html>
