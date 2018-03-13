package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LinkService;
import service.impl.LinkServiceImpl;
import util.Constant;
import bean.Link;

public class LinkServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	private LinkService linkService;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String oper = request.getParameter("oper");
		
		linkService = new LinkServiceImpl();
		
		if("add".equals(oper)) {
			doAdd(request,response);
		}else if("del".equals(oper)) {
			doDel(request, response);
		}else if("list".equals(oper)) {
			doList(request, response);
		}else if("edit".equals(oper)) {
			doEdit(request, response);
		}else if("findById".equals(oper)) {
			doFind(request,response);
		}
	}
	//点击编辑的时候，通过id 找到需要编辑的友情链接
	private void doFind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Link link = linkService.findLinkById(Integer.parseInt(id));
		request.setAttribute("link", link);
		request.getRequestDispatcher("admin/linkManage/editLink.jsp").forward(request, response);
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获得参数
		Integer id = Integer.parseInt(request.getParameter("id"));//注意：从隐藏域传递 过来的id 是字符串 还是 整型
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		Integer displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
		Integer blogId = Integer.parseInt(request.getParameter("blogId"));
		
		//封装数据
		Link link = new Link();
		
		link.setId(id);
		link.setName(name);
		link.setUrl(url);
		link.setDisplayOrder(displayOrder);
		link.setBlogId(blogId);
		
		boolean flag = linkService.editLink(link);
		if(flag) {
			response.sendRedirect("LinkServlet?oper=list");
		}else {
			response.sendRedirect("admin/linkManage/editLink.jsp");
		}
	}

	private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Link> list = linkService.listLink();
		request.setAttribute("list", list);
		request.getRequestDispatcher("admin/linkManage/linkListing.jsp").forward(request, response);
	}
	//删除友情列表
	private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//id这个地方容易报错 ，出现null异常,原因？？？
		Integer id= Integer.parseInt(request.getParameter("id"));
		
		//封装数据
		Link link = new Link();
		
		link.setId(id);
		
		boolean flag = linkService.deleteLink(link);
		if(flag) {
			response.sendRedirect("/myblog/LinkServlet?oper=list");
		}else {
			response.getWriter().println(
					"<script type='text/javascript'>" +
					"alert('删除失败!');" +
					"window.location.href='LinkServlet?oper=list'" +
					"</script>");
		}
	}
	//添加
	private void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String displayOrder = request.getParameter("displayOrder");
		
		Link link = new Link();
		
		link.setName(name);
		link.setUrl(url);
		link.setDisplayOrder(Integer.parseInt(displayOrder));
		
		link.setBlogId(Constant.BLOG_ID);
		
		boolean flag = linkService.addLink(link);
		if(flag) {
			response.sendRedirect("LinkServlet?oper=list");
		}else {
			response.sendRedirect("admin/linkManage/addLink.jsp");
		}
	}

}
