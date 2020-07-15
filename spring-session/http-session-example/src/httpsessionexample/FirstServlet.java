package httpsessionexample;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FirstServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            String name = request.getParameter("userName");
            out.print("Hi " + name);

            session.setAttribute("uname", name);
            out.print("</br> ");
            out.print("Session Id : " + session.getId());
            out.print("</br> ");
            out.print("<a href='secondservlet'>Second Servlet</a>");

            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
