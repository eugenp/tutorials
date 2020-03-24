
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import model.User;

/**
 * Servlet implementation class TestJPAServlet
 */
public class TestJPAServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName = "wildfly-jpa")
    EntityManager em;

    @Resource
    UserTransaction tx;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestJPAServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Query q = em.createNamedQuery("User.findAll");
        List<User> users = q.getResultList();
        response.getWriter()
            .append("JPA users returned: " + users.size());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
