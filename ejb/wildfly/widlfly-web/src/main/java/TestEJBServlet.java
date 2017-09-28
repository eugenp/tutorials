
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import wildfly.beans.UserBeanLocal;

/**
 * Servlet implementation class TestEJBServlet
 */
public class TestEJBServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserBeanLocal userBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userBean.getUsers();

        response.getWriter()
            .append("The number of users is: " + users.size());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
