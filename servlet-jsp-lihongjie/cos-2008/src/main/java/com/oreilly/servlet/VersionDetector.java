// Copyright (C) 2000-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

/** 
 * A class to determine the current Servlet API version number, and the
 * current JDK version number.  It looks at the available classes and
 * variables to make the determination.  The class can detect Servlet 
 * API versions up to 2.2, and JDK versions up to 1.3.
 * <p>
 * It can be used like this:
 * <blockquote><pre>
 * String servletVersion = VersionDetector.getServletVersion();
 * &nbsp;
 * String javaVersion = VersionDetector.getJavaVersion();
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 2000
 * @version 1.3, 2002/04/22, added detection of JDK 1.5 and Servlet API 2.4
 * @version 1.2, 2001/04/11, added detection of JDK 1.4
 * @version 1.1, 2000/09/22, added detection of Servlet API 2.3
 * @version 1.0, 2000/02/08
 */
public class VersionDetector {

  static String servletVersion;
  static String javaVersion;

  /**
   * Determines the Servlet API version number.
   *
   * @return a String representation of the servlet version
   */
  public static String getServletVersion() {
    if (servletVersion != null) {
      return servletVersion;
    }

    // Determine the servlet version by looking at available classes
    //   and variables
    // javax.servlet.http.HttpSession was introduced in Servlet API 2.0
    // javax.servlet.RequestDispatcher was introduced in Servlet API 2.1
    // javax.servlet.http.HttpServletResponse.SC_EXPECTATION_FAILED was
    //   introduced in Servlet API 2.2
    // javax.servlet.Filter is slated to be introduced in Servlet API 2.3
    // Count up versions until a NoClassDefFoundError or NoSuchFieldException
    //   ends the try
    String ver = null;
    try {
      ver = "1.0";
      Class.forName("javax.servlet.http.HttpSession");
      ver = "2.0";
      Class.forName("javax.servlet.RequestDispatcher");
      ver = "2.1"; 
      Class.forName("javax.servlet.http.HttpServletResponse")
                   .getDeclaredField("SC_EXPECTATION_FAILED");
      ver = "2.2";
      Class.forName("javax.servlet.Filter");
      ver = "2.3";
      Class.forName("javax.servlet.ServletRequestListener");
      ver = "2.4";
    }
    catch (Throwable t) {
    }
    
    servletVersion = ver;
    return servletVersion;                                              
  }

  /**
   * Determines the JDK version number.
   *
   * @return a String representation of the JDK version
   */
  public static String getJavaVersion() {
    if (javaVersion != null) {
      return javaVersion;
    }

    // Determine the Java version by looking at available classes
    // java.lang.Void was introduced in JDK 1.1
    // java.lang.ThreadLocal was introduced in JDK 1.2
    // java.lang.StrictMath was introduced in JDK 1.3
    // java.net.URI was introduced in JDK 1.4
    // java.lang.reflect.ParameterizedType is highly likely to be
    //   introduced in JDK 1.5
    // Count up versions until a NoClassDefFoundError ends the try
    String ver = null;
    try {
      ver = "1.0";
      Class.forName("java.lang.Void");
      ver = "1.1";
      Class.forName("java.lang.ThreadLocal");
      ver = "1.2"; 
      Class.forName("java.lang.StrictMath");
      ver = "1.3"; 
      Class.forName("java.net.URI");
      ver = "1.4";
      Class.forName("java.lang.reflect.ParameterizedType");
      ver = "1.5";
    }
    catch (Throwable t) {
    }

    javaVersion = ver;
    return javaVersion;
  }
}
