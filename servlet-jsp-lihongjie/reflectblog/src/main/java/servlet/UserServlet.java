package servlet;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private UserService userService;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //配置
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        //获得请求参数
        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        //创建Cookie
        Cookie cookie = new Cookie("user", username);
        //添加Cookie到响应对象中
        response.addCookie(cookie);
        //业务逻辑层进行处理
        userService = new UserServiceImpl();
        boolean flag = userService.validateLogin(username, pwd);
        //由于后面是请求转发，是同一个请求，所以可以再UserServlet中获得Cookie 而不用在"/admin/index.jsp"使用cookie

        //返回响应
        if (flag) {
            request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
        } else {
            response.sendRedirect("/myblog/admin/login.jsp");
        }
    }

}
