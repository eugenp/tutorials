package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CategoryService;
import service.impl.CategoryServiceImpl;
import util.Constant;
import bean.Category;
import bean.Pagination;

public class CategoryServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	private CategoryService categoryService;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String oper = request.getParameter("oper");
		
		categoryService = new CategoryServiceImpl();
		
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
		}else if("listByPage".equals(oper)) {//分页显示的操作
			
			doListByPage(request,response);
		}
	}
	private void doListByPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pageNumberStr = request.getParameter("pageNumber");
		
		int pageNumber = 1;
		if(pageNumberStr != null) {
			pageNumber = Integer.parseInt(pageNumberStr);
		}
		Pagination<Category> pagination = categoryService.getPage(null, Constant.PAGESIZE, pageNumber);
		request.setAttribute("pagination", pagination);
		request.getRequestDispatcher("admin/categoryManage/categoryList.jsp").forward(request, response);
	}
	/**
	 * 添加分类相关的跳转和页面返回
	 * @param request
	 * @param response
	 */
	private void doAdd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Category category = new Category();
		//获得参数
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String displayOrder = request.getParameter("displayOrder");
		
		category.setName(name);
		category.setDescription(description);
		category.setDisplayOrder(Integer.parseInt(displayOrder));
		
		category.setBlogId(Constant.BLOG_ID);
		
		boolean flag = categoryService.addCategory(category);
		
		if(flag) {
			response.sendRedirect("CategoryServlet?oper=list");
		}else {
			response.sendRedirect("admin/categoryManage/editCategory.jsp");
		}
	}
	//编辑
	private void doEdit(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Category category = new Category();
		//从xiugaiCategory.jsp获得参数,通过隐藏域传递过来的
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Integer blogId = Integer.parseInt(request.getParameter("blogId"));
		Integer displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
		//赋值
		category.setId(id);
		category.setName(name);
		category.setDescription(description);
		category.setBlogId(blogId);
		category.setDisplayOrder(displayOrder);
		
		boolean flag = categoryService.editCategory(category);
		
		if(flag) {
			response.sendRedirect("CategoryServlet?oper=listByPage");
		}else {
			response.sendRedirect("admin/categoryManage/xiugaiCategory.jsp");
		}
	}
	//分类列表，通过传递的参数不同，调用不同的方法
	private void doList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Category> list = categoryService.listCategory();
		request.setAttribute("list", list);
		request.getRequestDispatcher("admin/categoryManage/categoryList.jsp").forward(request, response);
	}
	//删除功能
	private void doDel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer id= Integer.parseInt(request.getParameter("id"));
		Category category = new Category();
		category.setId(id);
		boolean flag = categoryService.deleteCategory(category);
		if(flag) {
			response.sendRedirect("/myblog/CategoryServlet?oper=list");
		}else {
			//javascript 做的弹出删除失败的对话框,成功后然后用window对象跳转到CategoryServlet的分类列表中(或者用history.go(-1)回到前一个页面，避免操作数据库)
			response.getWriter().println(
			"<script type='text/javascript'>alert('删除失败!');window.location.href='CategoryServlet?oper=list'</script>");
		}

	}
	//编辑分类,根据id找到要编辑的类别信息。
	private void doFind(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		
		Category category = categoryService.findCategoryById(Integer.parseInt(id));
		request.setAttribute("category", category);
		request.getRequestDispatcher("admin/categoryManage/xiugaiCategory.jsp").forward(request, response);
	}
	
}
