// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * A request wrapper to support MultipartFilter.  
 * The filter capability requires Servlet API 2.3.
 * <p>
 * See Jason Hunter's June 2001 article in JavaWorld for a full explanation of 
 * the class usage.
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 2001
 * @version 1.1, 2002/11/15, added getOriginalFileName() to match
 *                           MultipartRequest
 * @version 1.0, 2001/06/19
 */
public class MultipartWrapper extends HttpServletRequestWrapper {

  MultipartRequest mreq = null;

  public MultipartWrapper(HttpServletRequest req, String dir)
                                     throws IOException {
    super(req);
    mreq = new MultipartRequest(req, dir);
  }

  // Methods to replace HSR methods
  public Enumeration getParameterNames() {
    return mreq.getParameterNames();
  }
  public String getParameter(String name) {
    return mreq.getParameter(name);
  }
  public String[] getParameterValues(String name) {
    return mreq.getParameterValues(name);
  }
  public Map getParameterMap() {
    Map map = new HashMap();
    Enumeration enumm = getParameterNames();
    while (enumm.hasMoreElements()) {
      String name = (String) enumm.nextElement();
      map.put(name, mreq.getParameterValues(name));
    }
    return map;
  }

  // Methods only in MultipartRequest
  public Enumeration getFileNames() {
    return mreq.getFileNames();
  }
  public String getFilesystemName(String name) {
    return mreq.getFilesystemName(name);
  }
  public String getOriginalFileName(String name) {
    return mreq.getOriginalFileName(name);
  }
  public String getContentType(String name) {
    return mreq.getContentType(name);
  }
  public File getFile(String name) {
    return mreq.getFile(name);
  }
}
