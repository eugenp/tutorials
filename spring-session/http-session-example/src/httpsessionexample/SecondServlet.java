package httpsessionexample;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecondServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            HttpSession session = request.getSession(true);
            String name = (String) session.getAttribute("uname");
            out.print("Hi " + name);
            out.print("</br> ");
            out.print("Session Id : " + session.getId());
            out.print("</br> ");
            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
