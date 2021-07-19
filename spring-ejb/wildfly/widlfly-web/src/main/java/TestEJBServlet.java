
import java.io.IOException;
import java.io.PrintWriter;
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

    @EJB
    private UserBeanLocal userBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userBean.getUsers();

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<body>");
        for (User user : users) {
            out.print(user.getUsername());
            out.print(" " + user.getEmail() + " <br>");
        }
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
