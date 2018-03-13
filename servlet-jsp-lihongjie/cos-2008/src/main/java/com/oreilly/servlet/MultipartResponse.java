// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** 
 * A utility class to generate <tt>multipart/x-mixed-replace</tt> responses,
 * the kind of responses that implement server push.  Note that Microsoft
 * Internet Explorer does not understand this sort of response.
 * <p>
 * To use this class, first construct a new MultipartResponse 
 * passing to its constructor the servlet's response parameter.  
 * MultipartResponse uses the response object to fetch the 
 * servlet's output stream and to set the response's content type.
 * <p>
 * Then, for each page of content, begin by calling <tt>startResponse()</tt>
 * passing in the content type for that page.  Send the content for the 
 * page by writing to the output stream as usual.  A call to 
 * <tt>endResponse()</tt> ends the page and flushes the content so the 
 * client can see it.  At this point a <tt>sleep()</tt> or other delay
 * can be added until the next page is ready for sending.
 * <p>
 * The call to <tt>endResponse()</tt> is optional.  The 
 * <tt>startResponse()</tt> method knows whether the last response has 
 * been ended, and ends it itself if necessary.  However, it's wise to 
 * call <tt>endResponse()</tt> if there's to be a delay between the
 * time one response ends and the next begins.  It lets the client display 
 * the latest response during the time it waits for the next one.
 * <p>
 * Finally, after each response page has been sent, a call to the 
 * <tt>finish()</tt> method finishes the multipart response and sends a 
 * code telling the client there will be no more responses.
 * <p>
 * For example:
 * <blockquote><pre>
 * MultipartResponse multi = new MultipartResponse(res);
 * &nbsp;
 * multi.startResponse("text/plain");
 * out.println("On your mark");
 * multi.endResponse();
 * &nbsp;
 * try { Thread.sleep(1000); } catch (InterruptedException e) { }
 * &nbsp;
 * multi.startResponse("text/plain");
 * out.println("Get set");
 * multi.endResponse();
 * &nbsp;
 * try { Thread.sleep(1000); } catch (InterruptedException e) { }
 * &nbsp;
 * multi.startResponse("image/gif");
 * ServletUtils.returnFile(req.getRealPath("/images/go.gif"), out);
 * &nbsp;
 * multi.finish();
 * </pre></blockquote>
 *
 * @see ServletUtils
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 1998
 * @version 1.0, 98/09/18
 */
public class MultipartResponse {

  HttpServletResponse res;
  ServletOutputStream out;
  boolean endedLastResponse = true;

  /**
   * Constructs a new MultipartResponse to send content to the given
   * servlet response.
   *
   * @param response the servlet response
   * @exception IOException if an I/O error occurs
   */
  public MultipartResponse(HttpServletResponse response) throws IOException {
    // Save the response object and output stream
    res = response;
    out = res.getOutputStream();

    // Set things up
    res.setContentType("multipart/x-mixed-replace;boundary=End");
    out.println();
    out.println("--End");
  }

  /**
   * Begins a single response with the specified content type.
   * This method knows whether the last response has been ended, and 
   * ends it itself if necessary.
   *
   * @param contentType the content type of this response part
   * @exception IOException if an I/O error occurs
   */
  public void startResponse(String contentType) throws IOException {
    // End the last response if necessary
    if (!endedLastResponse) {
      endResponse();
    }
    // Start the next one
    out.println("Content-type: " + contentType);
    out.println();
    endedLastResponse = false;
  }

  /**
   * Ends a single response.  Flushes the output.
   *
   * @exception IOException if an I/O error occurs
   */
  public void endResponse() throws IOException {
    // End the last response, and flush so the client sees the content
    out.println();
    out.println("--End");
    out.flush();
    endedLastResponse = true;
  }

  /**
   * Finishes the multipart response.  Sends a code telling the client
   * there will be no more responses and flushes the output.
   *
   * @exception IOException if an I/O error occurs
   */
  public void finish() throws IOException {
    out.println("--End--");
    out.flush();
  }
}
