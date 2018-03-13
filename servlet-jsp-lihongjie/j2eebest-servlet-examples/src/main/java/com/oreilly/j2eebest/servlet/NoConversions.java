package com.oreilly.j2eebest.servlet;

import java.io.*;
import java.util.prefs.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NoConversions extends HttpServlet {

  String name = "content.txt";  // demo file to send

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    String file = getServletContext().getRealPath(name);

    res.setContentType("text/plain");
    OutputStream out = res.getOutputStream();

    returnFile(file, out);
  }

  public static void returnFile(String filename, OutputStream out)
                             throws FileNotFoundException, IOException {
    InputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(filename));
      byte[] buf = new byte[4 * 1024];  // 4K buffer
      int bytesRead;
      while ((bytesRead = in.read(buf)) != -1) {
        out.write(buf, 0, bytesRead);
      }
    }
    finally {
      if (in != null) in.close();
    }
  }
}
