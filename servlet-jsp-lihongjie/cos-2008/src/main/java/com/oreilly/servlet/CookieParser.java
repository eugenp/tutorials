// Copyright (C) 1999-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** 
 * A class to simplify cookie retrieval.  It can retrieve cookie values by
 * name and return the value as any primitive type (no casting or parsing 
 * required).  It can also throw an exception when a cookie is not found 
 * (simplifying error handling), and can accept default values (eliminating 
 * error handling).
 * <p>
 * It is used like this:
 * <blockquote><pre>
 * CookieParser parser = new CookieParser(req);
 * &nbsp;
 * float ratio = parser.getFloatCookie("ratio", 1.0);
 * &nbsp;
 * int count = 0;
 * try {
 *   count = parser.getIntCookie("count");
 * }
 * catch (NumberFormatException e) {
 *   handleMalformedCount();
 * }
 * catch (CookieNotFoundException e) {
 *   handleNoCount();
 * }
 * </pre></blockquote>
 *
 * @see com.oreilly.servlet.CookieNotFoundException
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 2000
 * @version 1.0, 2000/03/19
 */
public class CookieParser {

  private HttpServletRequest req;
  private Hashtable cookieJar = new Hashtable();

  /**
   * Constructs a new CookieParser to handle the cookies of the
   * given request.
   *
   * @param req the servlet request
   */
  public CookieParser(HttpServletRequest req) {
    this.req = req;
    parseCookies();
  }

  // Load the cookie values into the cookies hashtable
  void parseCookies() {
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        String name = cookies[i].getName();
        String value = cookies[i].getValue();
        cookieJar.put(name, value);
      } 
    } 
  } 

  /**
   * Gets the named cookie value as a String
   *
   * @param name the cookie name
   * @return the cookie value as a String
   * @exception CookieNotFoundException if the cookie was not found
   */
  public String getStringCookie(String name)
      throws CookieNotFoundException {
    String value = (String) cookieJar.get(name);
    if (value == null)
      throw new CookieNotFoundException(name + " not found");
    else
      return value;
  }

  /**
   * Gets the named cookie value as a String, with a default.
   * Returns the default value if the cookie is not found
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a String, or the default
   */
  public String getStringCookie(String name, String def) {
    try { return getStringCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a boolean
   *
   * @param name the cookie name
   * @return the cookie value as a boolean
   * @exception CookieNotFoundException if the cookie was not found
   */
  public boolean getBooleanCookie(String name)
      throws CookieNotFoundException {
    return new Boolean(getStringCookie(name)).booleanValue();
  }

  /**
   * Gets the named cookie value as a boolean, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a boolean, or the default
   */
  public boolean getBooleanCookie(String name, boolean def) {
    try { return getBooleanCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a byte
   *
   * @param name the cookie name
   * @return the cookie value as a byte
   * @exception CookieNotFoundException if the cookie was not found
   * @exception NumberFormatException if the cookie value could not
   * be converted to a byte
   */
  public byte getByteCookie(String name)
      throws CookieNotFoundException, NumberFormatException {
    return Byte.parseByte(getStringCookie(name));
  }

  /**
   * Gets the named cookie value as a byte, with a default.
   * Returns the default value if the cookie is not found or cannot
   * be converted to a byte.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a byte, or the default
   */
  public byte getByteCookie(String name, byte def) {
    try { return getByteCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a char
   *
   * @param name the cookie name
   * @return the cookie value as a char
   * @exception CookieNotFoundException if the cookie was not found
   */
  public char getCharCookie(String name)
      throws CookieNotFoundException {
    String param = getStringCookie(name);
    if (param.length() == 0)
      throw new CookieNotFoundException(name + " is empty string");
    else
      return (param.charAt(0));
  }

  /**
   * Gets the named cookie value as a char, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a char, or the default
   */
  public char getCharCookie(String name, char def) {
    try { return getCharCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a double
   *
   * @param name the cookie name
   * @return the cookie value as a double
   * @exception CookieNotFoundException if the cookie was not found
   * @exception NumberFormatException if the cookie could not be converted
   * to a double
   */
  public double getDoubleCookie(String name)
      throws CookieNotFoundException, NumberFormatException {
    return new Double(getStringCookie(name)).doubleValue();
  }

  /**
   * Gets the named cookie value as a double, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a double, or the default
   */
  public double getDoubleCookie(String name, double def) {
    try { return getDoubleCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a float
   *
   * @param name the cookie name
   * @return the cookie value as a float
   * @exception CookieNotFoundException if the cookie was not found
   * @exception NumberFormatException if the cookie could not be converted
   * to a float
   */
  public float getFloatCookie(String name)
      throws CookieNotFoundException, NumberFormatException {
    return new Float(getStringCookie(name)).floatValue();
  }

  /**
   * Gets the named cookie value as a float, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a float, or the default
   */
  public float getFloatCookie(String name, float def) {
    try { return getFloatCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a int
   *
   * @param name the cookie name
   * @return the cookie value as a int
   * @exception CookieNotFoundException if the cookie was not found
   * @exception NumberFormatException if the cookie could not be converted
   * to a int
   */
  public int getIntCookie(String name)
      throws CookieNotFoundException, NumberFormatException {
    return Integer.parseInt(getStringCookie(name));
  }

  /**
   * Gets the named cookie value as a int, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a int, or the default
   */
  public int getIntCookie(String name, int def) {
    try { return getIntCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a long
   *
   * @param name the cookie name
   * @return the cookie value as a long
   * @exception CookieNotFoundException if the cookie was not found
   * @exception NumberFormatException if the cookie could not be converted
   * to a long
   */
  public long getLongCookie(String name)
      throws CookieNotFoundException, NumberFormatException {
    return Long.parseLong(getStringCookie(name));
  }

  /**
   * Gets the named cookie value as a long, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a long, or the default
   */
  public long getLongCookie(String name, long def) {
    try { return getLongCookie(name); }
    catch (Exception e) { return def; }
  }

  /**
   * Gets the named cookie value as a short
   *
   * @param name the cookie name
   * @return the cookie value as a short
   * @exception CookieNotFoundException if the cookie was not found
   * @exception NumberFormatException if the cookie could not be converted
   * to a short
   */
  public short getShortCookie(String name)
      throws CookieNotFoundException, NumberFormatException {
    return Short.parseShort(getStringCookie(name));
  }

  /**
   * Gets the named cookie value as a short, with a default.
   * Returns the default value if the cookie is not found.
   * 
   * @param name the cookie name
   * @param def the default cookie value
   * @return the cookie value as a short, or the default
   */
  public short getShortCookie(String name, short def) {
    try { return getShortCookie(name); }
    catch (Exception e) { return def; }
  }
}
