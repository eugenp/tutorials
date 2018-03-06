// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;

/** 
 * A class to simplify parameter handling.  It can return parameters of
 * any primitive type (no casting or parsing required), can throw an 
 * exception when a parameter is not found (simplifying error handling),
 * and can accept default values (eliminating error handling).
 * <p>
 * It is used like this:
 * <blockquote><pre>
 * ParameterParser parser = new ParameterParser(req);
 * &nbsp;
 * float ratio = parser.getFloatParameter("ratio", 1.0);
 * &nbsp;
 * int count = 0;
 * try {
 *   count = parser.getIntParameter("count");
 * }
 * catch (NumberFormatException e) {
 *   handleMalformedCount();
 * }
 * catch (ParameterNotFoundException e) {
 *   handleNoCount();
 * }
 * </pre></blockquote>
 *
 * There's also a capability to find out if any required parameters are
 * missing from a request:
 * <blockquote><pre>
 * ParameterParser parser = new ParameterParser(req);
 * String[] required = { "fname", "lname", "account" };
 * String[] missing = parser.getMissingParameters(required);
 * </pre></blockquote>
 *
 * The default charset for input parameters is ISO-8859-1 (Latin-1).  
 * If the parameter values are encoded in another format, specify that using
 * setCharacterEncoding() before parsing.  The parameter names currently
 * have to be in the Latin-1 character set:
 * <blockquote><pre>
 * ParameterParser parser = new ParameterParser(req);
 * parser.setCharacterEncoding("Shift_JIS");
 * String japaneseValue = parser.getStringParameter("latinName");
 * </pre></blockquote>
 *
 * @see com.oreilly.servlet.ParameterNotFoundException
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 1998, 1999
 * @version 1.4, 2000/12/14, better checking the selected encoding is valid in 
 *                           setCharacterEncoding() thanks to Dewayne McNair
 * @version 1.3, 2000/05/17, added setCharacterEncoding()
 * @version 1.2, 2000/05/17, getBooleanParameter() now recognizes "on" and "yes"
 * @version 1.1, 1999/12/20, added getMissingParameters() method
 * @version 1.0, 1998/09/18
 */
public class ParameterParser {

  private ServletRequest req;
  private String encoding;

  /**
   * Constructs a new ParameterParser to handle the parameters of the
   * given request.
   *
   * @param req the servlet request
   */
  public ParameterParser(ServletRequest req) {
    this.req = req;
  }

  /**
   * Sets the character encoding (charset) of the request to help the parser 
   * properly decode parameter values.  The default is to return undecoded values,
   * the same as would be returned by getParameter().
   *
   * @param encoding the charset of the request
   * @exception UnsupportedEncodingException if the charset is not supported 
   * on this sytem
   */
  public void setCharacterEncoding(String encoding) 
                 throws UnsupportedEncodingException {
    // Test the encoding is valid
    new String("".getBytes("8859_1"), encoding);
    // Getting here means we're valid, so set the encoding
    this.encoding = encoding;
  }

  /**
   * Gets the named parameter value as a String
   *
   * @param name the parameter name
   * @return the parameter value as a String
   * @exception ParameterNotFoundException if the parameter was not found
   * or was the empty string
   */
  public String getStringParameter(String name)
      throws ParameterNotFoundException {
    String[] values = req.getParameterValues(name);
    if (values == null) {
      throw new ParameterNotFoundException(name + " not found");
    }
    else if (values[0].length() == 0) {
      throw new ParameterNotFoundException(name + " was empty");
    }
    else {
      if (encoding == null) {
        return values[0];
      }
      else {
        try {
          return new String(values[0].getBytes("8859_1"), encoding);
        }
        catch (UnsupportedEncodingException e) {
          return values[0];  // should never happen
        }
      }
    }
  }

  /**
   * Gets the named parameter value as a String, with a default.
   * Returns the default value if the parameter is not found or 
   * is the empty string.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a String, or the default
   */
  public String getStringParameter(String name, String def) {
    try { return getStringParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a boolean, with true indicated by
   * "true", "on", or "yes" in any letter case, false indicated by "false", 
   * "off", or "no" in any letter case.
   *
   * @param name the parameter name
   * @return the parameter value as a boolean
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter could not be converted 
   * to a boolean
   */
  public boolean getBooleanParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    String value = getStringParameter(name).toLowerCase();
    if ((value.equalsIgnoreCase("true")) ||
        (value.equalsIgnoreCase("on")) ||
        (value.equalsIgnoreCase("yes"))) {
        return true;
    }
    else if ((value.equalsIgnoreCase("false")) ||
             (value.equalsIgnoreCase("off")) ||
             (value.equalsIgnoreCase("no"))) {
        return false;
    }
    else {
      throw new NumberFormatException("Parameter " + name + " value " + value + 
                                      " is not a boolean");
    }
  }

  /**
   * Gets the named parameter value as a boolean, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a boolean, or the default
   */
  public boolean getBooleanParameter(String name, boolean def) {
    try { return getBooleanParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a byte
   *
   * @param name the parameter name
   * @return the parameter value as a byte
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter value could not
   * be converted to a byte
   */
  public byte getByteParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    return Byte.parseByte(getStringParameter(name));
  }

  /**
   * Gets the named parameter value as a byte, with a default.
   * Returns the default value if the parameter is not found or cannot
   * be converted to a byte.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a byte, or the default
   */
  public byte getByteParameter(String name, byte def) {
    try { return getByteParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a char
   *
   * @param name the parameter name
   * @return the parameter value as a char
   * @exception ParameterNotFoundException if the parameter was not found
   * or was the empty string
   */
  public char getCharParameter(String name)
      throws ParameterNotFoundException {
    String param = getStringParameter(name);
    if (param.length() == 0)
      throw new ParameterNotFoundException(name + " is empty string");
    else
      return (param.charAt(0));
  }

  /**
   * Gets the named parameter value as a char, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a char, or the default
   */
  public char getCharParameter(String name, char def) {
    try { return getCharParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a double
   *
   * @param name the parameter name
   * @return the parameter value as a double
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter could not be converted
   * to a double
   */
  public double getDoubleParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    return new Double(getStringParameter(name)).doubleValue();
  }

  /**
   * Gets the named parameter value as a double, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a double, or the default
   */
  public double getDoubleParameter(String name, double def) {
    try { return getDoubleParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a float
   *
   * @param name the parameter name
   * @return the parameter value as a float
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter could not be converted
   * to a float
   */
  public float getFloatParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    return new Float(getStringParameter(name)).floatValue();
  }

  /**
   * Gets the named parameter value as a float, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a float, or the default
   */
  public float getFloatParameter(String name, float def) {
    try { return getFloatParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a int
   *
   * @param name the parameter name
   * @return the parameter value as a int
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter could not be converted
   * to a int
   */
  public int getIntParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    return Integer.parseInt(getStringParameter(name));
  }

  /**
   * Gets the named parameter value as a int, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a int, or the default
   */
  public int getIntParameter(String name, int def) {
    try { return getIntParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a long
   *
   * @param name the parameter name
   * @return the parameter value as a long
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter could not be converted
   * to a long
   */
  public long getLongParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    return Long.parseLong(getStringParameter(name));
  }

  /**
   * Gets the named parameter value as a long, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a long, or the default
   */
  public long getLongParameter(String name, long def) {
    try { return getLongParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named parameter value as a short
   *
   * @param name the parameter name
   * @return the parameter value as a short
   * @exception ParameterNotFoundException if the parameter was not found
   * @exception NumberFormatException if the parameter could not be converted
   * to a short
   */
  public short getShortParameter(String name)
      throws ParameterNotFoundException, NumberFormatException {
    return Short.parseShort(getStringParameter(name));
  }

  /**
   * Gets the named parameter value as a short, with a default.
   * Returns the default value if the parameter is not found.
   * 
   * @param name the parameter name
   * @param def the default parameter value
   * @return the parameter value as a short, or the default
   */
  public short getShortParameter(String name, short def) {
    try { return getShortParameter(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Determines which of the required parameters were missing from the
   * request.  Returns null if all the parameters are present.
   * 
   * @param required array of required parameters
   * @return an array of missing parameters, or null if none are missing
   */
  public String[] getMissingParameters(String[] required) {
    Vector missing = new Vector();
    for (int i = 0; i < required.length; i++) {
      String val = getStringParameter(required[i], null);
      if (val == null) {
        missing.addElement(required[i]);
      }
    }
    if (missing.size() == 0) {
      return null;
    }
    else {
      String[] ret = new String[missing.size()];
      missing.copyInto(ret);
      return ret;
    }
  }
}
