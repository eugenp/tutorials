package com.oreilly.j2eebest.servlet;

import java.io.*;
import java.util.prefs.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class WastedConversions extends HttpServlet {

    // Random file, for demo purposes only
    String name = "content.txt";

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String file = getServletContext().getRealPath(name);

        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        returnFile(file, out);
    }

    public static void returnFile(String filename, Writer out)
            throws FileNotFoundException, IOException {
        Reader in = null;
        try {
            in = new BufferedReader(new FileReader(filename));
            char[] buf = new char[4 * 1024];  // 4K char buffer
            int charsRead;
            while ((charsRead = in.read(buf)) != -1) {
                out.write(buf, 0, charsRead);
            }
        } finally {
            if (in != null) in.close();
        }
    }
}
