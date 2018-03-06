// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet.multipart;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;

/** 
 * A utility class to handle <code>multipart/form-data</code> requests,
 * the kind of requests that support file uploads.  This class uses a 
 * "pull" model where the reading of incoming files and parameters is 
 * controlled by the client code, which allows incoming files to be stored 
 * into any <code>OutputStream</code>.  If you wish to use an API which 
 * resembles <code>HttpServletRequest</code>, use the "push" model 
 * <code>MultipartRequest</code> instead.  It's an easy-to-use wrapper 
 * around this class.
 * <p>
 * This class can receive arbitrarily large files (up to an artificial limit 
 * you can set), and fairly efficiently too.  
 * It cannot handle nested data (multipart content within multipart content).
 * It <b>can</b> now with the latest release handle internationalized content
 * (such as non Latin-1 filenames).
 * <p>
 * It also optionally includes enhanced buffering and Content-Length
 * limitation.  Buffering is only required if your servlet container is 
 * poorly implemented (many are, including Tomcat 3.2),
 * but it is generally recommended because it will make a slow servlet 
 * container a lot faster, and will only make a fast servlet container a 
 * little slower.  Content-Length limiting is usually only required if you find 
 * that your servlet is hanging trying to read the input stram from the POST, 
 * and it is similarly recommended because it only has a minimal impact on 
 * performance.
 * <p>
 * See the included upload.war for an example of how to use this class.
 * <p>
 * The full file upload specification is contained in experimental RFC 1867,
 * available at <a href="http://www.ietf.org/rfc/rfc1867.txt">
 * http://www.ietf.org/rfc/rfc1867.txt</a>.
 * 
 * @see com.oreilly.servlet.MultipartRequest
 * 
 * @author Jason Hunter
 * @author Geoff Soutter
 * @version 1.13, 2004/09/01, added workaround if content-length is -1
 * @version 1.12, 2004/05/17, added trim on disposition
 * @version 1.11, 2002/11/01, added constructor that takes an encoding, to
 *                            make sure chars are always read correctly
 * @version 1.10, 2002/11/01, added support for a preamble before the first
 *                            boundary marker
 * @version 1.9, 2002/11/01, added support to parse odd Opera Content-Type
 * @version 1.8, 2002/11/01, added support for lynx with unquoted param vals
 * @version 1.7, 2002/04/30, fixed bug if a line was '\n' alone
 * @version 1.6, 2002/04/30, added better internationalization support, thanks
 *                           to Changshin Lee
 * @version 1.5, 2002/04/30, added Opera header fix, thanks to Nic Ferrier
 * @version 1.4, 2001/03/23, added IE5 bug workaround supporting \n as line
 *                           ending, thanks to Michael Alyn Miller
 * @version 1.3, 2001/01/22, added support for boundaries surrounded by quotes
 *                           and content-disposition after content-type,
 *                           thanks to Scott Stark
 * @version 1.2, 2001/01/22, getFilePath() support thanks to Stefan Eissing
 * @version 1.1, 2000/10/29, integrating old WebSphere fix
 * @version 1.0, 2000/10/27, initial revision
 */
public class MultipartParser {
  
  /** input stream to read parts from */
  private ServletInputStream in;
  
  /** MIME boundary that delimits parts */
  private String boundary;
  
  /** reference to the last file part we returned */
  private FilePart lastFilePart;

  /** buffer for readLine method */
  private byte[] buf = new byte[8 * 1024];
  
  /** default encoding */
  private static String DEFAULT_ENCODING = "ISO-8859-1";

  /** preferred encoding */
  private String encoding = DEFAULT_ENCODING;

  /**
   * Creates a <code>MultipartParser</code> from the specified request,
   * which limits the upload size to the specified length, buffers for 
   * performance and prevent attempts to read past the amount specified 
   * by the Content-Length.
   * 
   * @param req   the servlet request.
   * @param maxSize the maximum size of the POST content.
   */
  public MultipartParser(HttpServletRequest req, 
                         int maxSize) throws IOException {
    this(req, maxSize, true, true);
  }
  
  /**
   * Creates a <code>MultipartParser</code> from the specified request,
   * which limits the upload size to the specified length, and optionally 
   * buffers for performance and prevents attempts to read past the amount 
   * specified by the Content-Length. 
   * 
   * @param req   the servlet request.
   * @param maxSize the maximum size of the POST content.
   * @param buffer whether to do internal buffering or let the server buffer,
   *               useful for servers that don't buffer
   * @param limitLength boolean flag to indicate if we need to filter 
   *                    the request's input stream to prevent trying to 
   *                    read past the end of the stream.
   */
  public MultipartParser(HttpServletRequest req, int maxSize, boolean buffer, 
                         boolean limitLength) throws IOException {
    this(req, maxSize, buffer, limitLength, null);
  }

  /**
   * Creates a <code>MultipartParser</code> from the specified request,
   * which limits the upload size to the specified length, and optionally 
   * buffers for performance and prevents attempts to read past the amount 
   * specified by the Content-Length, and with a specified encoding. 
   * 
   * @param req   the servlet request.
   * @param maxSize the maximum size of the POST content.
   * @param buffer whether to do internal buffering or let the server buffer,
   *               useful for servers that don't buffer
   * @param limitLength boolean flag to indicate if we need to filter 
   *                    the request's input stream to prevent trying to 
   *                    read past the end of the stream.
   * @param encoding the encoding to use for parsing, default is ISO-8859-1.
   */
  public MultipartParser(HttpServletRequest req, int maxSize, boolean buffer, 
                         boolean limitLength, String encoding)
                                                throws IOException {
    // First make sure we know the encoding to handle chars correctly.
    // Thanks to Andreas Granzer, andreas.granzer@wave-solutions.com,
    // for pointing out the need to have this in the constructor.
    if (encoding != null) {
      setEncoding(encoding);
    }

    // Check the content type to make sure it's "multipart/form-data"
    // Access header two ways to work around WebSphere oddities
    String type = null;
    String type1 = req.getHeader("Content-Type");
    String type2 = req.getContentType();
    // If one value is null, choose the other value
    if (type1 == null && type2 != null) {
      type = type2;
    }
    else if (type2 == null && type1 != null) {
      type = type1;
    }
    // If neither value is null, choose the longer value
    else if (type1 != null && type2 != null) {
      type = (type1.length() > type2.length() ? type1 : type2);
    }

    if (type == null || 
        !type.toLowerCase().startsWith("multipart/form-data")) {
      throw new IOException("Posted content type isn't multipart/form-data");
    }

    // Check the content length to prevent denial of service attacks
    int length = req.getContentLength();
    if (length > maxSize) {
      throw new IOException("Posted content length of " + length + 
                            " exceeds limit of " + maxSize);
    }

    // Get the boundary string; it's included in the content type.
    // Should look something like "------------------------12012133613061"
    String boundary = extractBoundary(type);
    if (boundary == null) {
      throw new IOException("Separation boundary was not specified");
    }

    ServletInputStream in = req.getInputStream();
    
    // If required, wrap the real input stream with classes that 
    // "enhance" its behaviour for performance and stability
    if (buffer) {
      in = new BufferedServletInputStream(in);
    }
    if (limitLength && length > 0) {
      in = new LimitedServletInputStream(in, length);
    }

    // Save our values for later
    this.in = in;
    this.boundary = boundary;
    
    // Read until we hit the boundary
    // Some clients send a preamble (per RFC 2046), so ignore that
    // Thanks to Ben Johnson, ben.johnson@merrillcorp.com, for pointing out
    // the need for preamble support.
    do {
      String line = readLine();
      if (line == null) {
        throw new IOException("Corrupt form data: premature ending");
      }
      // See if this line is the boundary, and if so break
      if (line.startsWith(boundary)) {
        break;  // success
      }
    } while (true);
  }

  /**
   * Sets the encoding used to parse from here onward.  The default is
   * ISO-8859-1.  Encodings are actually best passed into the contructor,
   * so even the initial line reads are correct.
   *
   * @param encoding The encoding to use for parsing
   */
   public void setEncoding(String encoding) {
     this.encoding = encoding;
   }

  /**
   * Read the next part arriving in the stream. Will be either a 
   * <code>FilePart</code> or a <code>ParamPart</code>, or <code>null</code>
   * to indicate there are no more parts to read. The order of arrival 
   * corresponds to the order of the form elements in the submitted form.
   * 
   * @return either a <code>FilePart</code>, a <code>ParamPart</code> or
   *        <code>null</code> if there are no more parts to read.
   * @exception IOException	if an input or output exception has occurred.
   * 
   * @see FilePart
   * @see ParamPart
   */
  public Part readNextPart() throws IOException {
    // Make sure the last file was entirely read from the input
    if (lastFilePart != null) {
      lastFilePart.getInputStream().close();
      lastFilePart = null;
    }
    
    // Read the headers; they look like this (not all may be present):
    // Content-Disposition: form-data; name="field1"; filename="file1.txt"
    // Content-Type: type/subtype
    // Content-Transfer-Encoding: binary
    Vector headers = new Vector();

    String line = readLine();
    if (line == null) {
      // No parts left, we're done
      return null;
    }
    else if (line.length() == 0) {
      // IE4 on Mac sends an empty line at the end; treat that as the end.
      // Thanks to Daniel Lemire and Henri Tourigny for this fix.
      return null;
    }

    // Read the following header lines we hit an empty line
    // A line starting with whitespace is considered a continuation;
    // that requires a little special logic.  Thanks to Nic Ferrier for
    // identifying a good fix.
    while (line != null && line.length() > 0) {
      String nextLine = null;
      boolean getNextLine = true;
      while (getNextLine) {
        nextLine = readLine();
        if (nextLine != null
            && (nextLine.startsWith(" ")
      	  || nextLine.startsWith("\t"))) {
          line = line + nextLine;
        }
        else {
          getNextLine = false;
        }
      }
      // Add the line to the header list
      headers.addElement(line);
      line = nextLine;
    }

    // If we got a null above, it's the end
    if (line == null) {
      return null;
    }

    String name = null;
    String filename = null;
    String origname = null;
    String contentType = "text/plain";  // rfc1867 says this is the default

    Enumeration enu = headers.elements();
    while (enu.hasMoreElements()) {
      String headerline = (String) enu.nextElement();
      if (headerline.toLowerCase().startsWith("content-disposition:")) {
        // Parse the content-disposition line
        String[] dispInfo = extractDispositionInfo(headerline);
        // String disposition = dispInfo[0];  // not currently used
        name = dispInfo[1];
        filename = dispInfo[2];
        origname = dispInfo[3];
      }
      else if (headerline.toLowerCase().startsWith("content-type:")) {
        // Get the content type, or null if none specified
        String type = extractContentType(headerline);
        if (type != null) {
          contentType = type;
        }
      }
    }

    // Now, finally, we read the content (end after reading the boundary)
    if (filename == null) {
      // This is a parameter, add it to the vector of values
      // The encoding is needed to help parse the value
      return new ParamPart(name, in, boundary, encoding);
    }
    else {
      // This is a file
      if (filename.equals("")) {
        filename = null; // empty filename, probably an "empty" file param
      }
      lastFilePart = new FilePart(name, in, boundary,
                                  contentType, filename, origname);
      return lastFilePart;
    }
  }
  
  /**
   * Extracts and returns the boundary token from a line.
   * 
   * @return the boundary token.
   */
  private String extractBoundary(String line) {
    // Use lastIndexOf() because IE 4.01 on Win98 has been known to send the
    // "boundary=" string multiple times.  Thanks to David Wall for this fix.
    int index = line.lastIndexOf("boundary=");
    if (index == -1) {
      return null;
    }
    String boundary = line.substring(index + 9);  // 9 for "boundary="
    if (boundary.charAt(0) == '"') {
      // The boundary is enclosed in quotes, strip them
      index = boundary.lastIndexOf('"');
      boundary = boundary.substring(1, index);
    }

    // The real boundary is always preceeded by an extra "--"
    boundary = "--" + boundary;

    return boundary;
  }

  /**
   * Extracts and returns disposition info from a line, as a <code>String<code>
   * array with elements: disposition, name, filename.
   * 
   * @return String[] of elements: disposition, name, filename.
   * @exception  IOException if the line is malformatted.
   */
  private String[] extractDispositionInfo(String line) throws IOException {
    // Return the line's data as an array: disposition, name, filename
    String[] retval = new String[4];

    // Convert the line to a lowercase string without the ending \r\n
    // Keep the original line for error messages and for variable names.
    String origline = line;
    line = origline.toLowerCase();

    // Get the content disposition, should be "form-data"
    int start = line.indexOf("content-disposition: ");
    int end = line.indexOf(";");
    if (start == -1 || end == -1) {
      throw new IOException("Content disposition corrupt: " + origline);
    }
    String disposition = line.substring(start + 21, end).trim();
    if (!disposition.equals("form-data")) {
      throw new IOException("Invalid content disposition: " + disposition);
    }

    // Get the field name
    start = line.indexOf("name=\"", end);  // start at last semicolon
    end = line.indexOf("\"", start + 7);   // skip name=\"
    int startOffset = 6;
    if (start == -1 || end == -1) {
      // Some browsers like lynx don't surround with ""
      // Thanks to Deon van der Merwe, dvdm@truteq.co.za, for noticing
      start = line.indexOf("name=", end);
      end = line.indexOf(";", start + 6);
      if (start == -1) {
        throw new IOException("Content disposition corrupt: " + origline);
      }
      else if (end == -1) {
        end = line.length();
      }
      startOffset = 5;  // without quotes we have one fewer char to skip
    }
    String name = origline.substring(start + startOffset, end);

    // Get the filename, if given
    String filename = null;
    String origname = null;
    start = line.indexOf("filename=\"", end + 2);  // start after name
    end = line.indexOf("\"", start + 10);          // skip filename=\"
    if (start != -1 && end != -1) {                // note the !=
      filename = origline.substring(start + 10, end);
      origname = filename;
      // The filename may contain a full path.  Cut to just the filename.
      int slash =
        Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
      if (slash > -1) {
        filename = filename.substring(slash + 1);  // past last slash
      }
    }

    // Return a String array: disposition, name, filename
    // empty filename denotes no file posted!
    retval[0] = disposition;
    retval[1] = name;
    retval[2] = filename;
    retval[3] = origname;
    return retval;
  }

  /**
   * Extracts and returns the content type from a line, or null if the
   * line was empty.
   * 
   * @return content type, or null if line was empty.
   * @exception  IOException if the line is malformatted.
   */
  private static String extractContentType(String line) throws IOException {
    // Convert the line to a lowercase string
    line = line.toLowerCase();

    // Get the content type, if any
    // Note that Opera at least puts extra info after the type, so handle
    // that.  For example:  Content-Type: text/plain; name="foo"
    // Thanks to Leon Poyyayil, leon.poyyayil@trivadis.com, for noticing this.
    int end = line.indexOf(";");
    if (end == -1) {
      end = line.length();
    }

    return line.substring(13, end).trim();  // "content-type:" is 13
  }
  
  /**
   * Read the next line of input.
   * 
   * @return     a String containing the next line of input from the stream,
   *        or null to indicate the end of the stream.
   * @exception IOException	if an input or output exception has occurred.
   */
  private String readLine() throws IOException {
    StringBuffer sbuf = new StringBuffer();
    int result;
    String line;

    do {
      result = in.readLine(buf, 0, buf.length);  // does +=
      if (result != -1) {
        sbuf.append(new String(buf, 0, result, encoding));
      }
    } while (result == buf.length);  // loop only if the buffer was filled

    if (sbuf.length() == 0) {
      return null;  // nothing read, must be at the end of stream
    }

    // Cut off the trailing \n or \r\n
    // It should always be \r\n but IE5 sometimes does just \n
    // Thanks to Luke Blaikie for helping make this work with \n
    int len = sbuf.length();
    if (len >= 2 && sbuf.charAt(len - 2) == '\r') {
      sbuf.setLength(len - 2);  // cut \r\n
    }
    else if (len >= 1 && sbuf.charAt(len - 1) == '\n') {
      sbuf.setLength(len - 1);  // cut \n
    }
    return sbuf.toString();
  }
}
