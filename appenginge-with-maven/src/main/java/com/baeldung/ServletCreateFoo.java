package com.baeldung;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.baeldung.dao.DatastoreFooDao;
import com.baeldung.dao.FooDao;

public class ServletCreateFoo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FooDao fooDao = new DatastoreFooDao();

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        User user = (User) req.getAttribute("user");
        UserService userService = UserServiceFactory.getUserService();
        if (user == null) {
            user = userService.getCurrentUser();
        }
        String name = checkNull(req.getParameter("name"));
        String longDescription = checkNull(req.getParameter("description"));
        fooDao.insertFoos(user.getUserId(), name, longDescription);
        req.setAttribute("result", fooDao.getFoos(user.getUserId()));
        getServletContext().getRequestDispatcher("/fooclient.jsp").forward(req,
                resp);
    }

    private String checkNull(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }
}
