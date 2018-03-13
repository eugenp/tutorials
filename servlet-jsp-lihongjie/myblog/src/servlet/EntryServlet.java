package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.EntryService;
import service.impl.CategoryServiceImpl;
import service.impl.EntryServiceImpl;
import vo.EntryVo;
import bean.Category;
import bean.Entry;

public class EntryServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}
	
	private EntryService entryService;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		entryService = new EntryServiceImpl();
		String oper = request.getParameter("oper");
		
		if("add".equals(oper)) {
			doAdd(request,response);
		}else if("del".equals(oper)) {
			doDel(request, response);
		}else if("list".equals(oper)) {
			doList(request, response);
		}else if("edit".equals(oper)) {
			doEdit(request, response);
		}else if("findById".equals(oper)) {
			doFindById(request,response);
		}else if("loadCategory".equals(oper)) {
			doLoad(oper,request,response);
		}
	}
	//加载 多带个参数oper
	private void doLoad(String oper,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//加载下拉列表中的类别信息
		List<Category> list = new CategoryServiceImpl().listCategory();
		request.setAttribute("categoryList", list);
		if("loadCategory".equals(oper)) {
			request.getRequestDispatcher("admin/articleManage/addArticle.jsp").forward(request, response);
		}else {
			
		}
	}
	private void doAdd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//
		String title = request.getParameter("title");
		String categoryId = request.getParameter("categoryId");
		String allowComment = request.getParameter("allowComment");
		String status = request.getParameter("status");
		String content = request.getParameter("content");
		
		Entry entry = new Entry();
		entry.setTitle(title);
		entry.setAllowComment(Integer.parseInt(allowComment));
		entry.setCategoryId(Integer.parseInt(categoryId));
		entry.setContent(content);
		entry.setStatus(Integer.parseInt(status));
		entry.setCreateTime(new Date());
		//初始化评论数等
		entry.setHits(0);
		entry.setCommentHit(0);
		
		boolean flag = entryService.addEntry(entry);
		if(flag) {
			response.sendRedirect("EntryServlet?oper=list");
		}else {
			response.getWriter().println(
					"<script type='text/javascript'>alert('添加失败!');window.location.href='admin/articleManage/addArticle.jsp'</script>");
		}
		
	}
	//删除文章
	private void doDel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Entry entry = new Entry();
		entry.setId(id);
		boolean flag = entryService.deleteEntry(entry);
		if(flag) {
			response.sendRedirect("/myblog/EntryServlet?oper=list");
		}else {
			response.getWriter().println(
					"<script type='text/javascript'>alert('删除失败!');window.location.href='EntryServlet?oper=list'</script>");
		}
	}
	private void doList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<EntryVo> list = entryService.listEntryVo();
		//list中没有类别的名字，只有id
		//在我们所返回的List当中，没有类别的名字，只有类别的ID---使用vo值对象
		request.setAttribute("list", list);
		request.getRequestDispatcher("admin/articleManage/articleList.jsp").forward(request, response);
	}
	private void doEdit(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String id = request.getParameter("id");
		String commentHit = request.getParameter("commentHit");
		String hits = request.getParameter("hits");
		String title = request.getParameter("title");
		String categoryId = request.getParameter("categoryId");
		String allowComment = request.getParameter("allowComment");
		String status = request.getParameter("status");
		String content = request.getParameter("content");
		
		String createTime = request.getParameter("createTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");

		Entry entry = new Entry();
		entry.setId(Integer.parseInt(id));
		entry.setCommentHit(Integer.parseInt(commentHit));
		entry.setHits(Integer.parseInt(hits));
		entry.setTitle(title);
		entry.setAllowComment(Integer.parseInt(allowComment));
		entry.setCategoryId(Integer.parseInt(categoryId));
		entry.setContent(content);
		entry.setStatus(Integer.parseInt(status));
		//处理时间对象转为字符串
		try {
			entry.setCreateTime(sdf.parse(createTime));
			System.out.println(entry.getCreateTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		boolean flag = entryService.editEntry(entry);
		if(flag) {
			response.sendRedirect("EntryServlet?oper=list");
		}else {
			response.getWriter().println(
					"<script type='text/javascript'>alert('修改失败!');history.go(-1)</script>");
		}
		
	}
	private void doFindById(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		Entry entry = entryService.findById(id);
		request.setAttribute("entry", entry);
		doLoad(null, request, response);
		request.getRequestDispatcher("admin/articleManage/editEntry.jsp").forward(request, response);
	}

}
