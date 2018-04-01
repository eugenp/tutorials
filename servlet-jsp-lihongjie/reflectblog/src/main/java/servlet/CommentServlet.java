package servlet;

import bean.Comment;
import service.CommentService;
import service.impl.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CommentServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    private CommentService commentService;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        String oper = request.getParameter("oper");

        commentService = new CommentServiceImpl();

        if ("list".equals(oper)) {
            doList(request, response);
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Comment> list = commentService.listComment();
        request.setAttribute("list", list);
        request.getRequestDispatcher("admin/commentManage/commentList.jsp").forward(request, response);
    }

}
