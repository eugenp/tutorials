package com.baeldung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baeldung.dao.DatastoreFooDao;
import com.baeldung.dao.FooDao;
import com.baeldung.model.Foo;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ServletGetFoos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FooDao fooDao = new DatastoreFooDao();

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String url = userService.createLoginURL(req.getRequestURI());
        String urlLinktext = "Login";
        List<Foo> foos = new ArrayList<Foo>();
        if (user != null) {
            url = userService.createLogoutURL(req.getRequestURI());
            urlLinktext = "Logout";
            foos = fooDao.getFoos(user.getUserId());
        }
        req.setAttribute("user", user);
        req.setAttribute("result", foos);
        req.setAttribute("urLinktext", urlLinktext);
        req.setAttribute("url", url);

        getServletContext().getRequestDispatcher("/fooclient.jsp").forward(req,
                resp);

    }

}
