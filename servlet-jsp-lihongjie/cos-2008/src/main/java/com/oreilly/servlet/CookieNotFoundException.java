// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

/** 
 * Thrown to indicate a cookie does not exist.
 *
 * @see com.oreilly.servlet.CookieParser
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 2000
 * @version 1.0, 2000/03/19
 */
public class CookieNotFoundException extends Exception {

  /**
   * Constructs a new CookieNotFoundException with no detail message.
   */
  public CookieNotFoundException() {
    super();
  }

  /**
   * Constructs a new CookieNotFoundException with the specified
   * detail message.
   *
   * @param s the detail message
   */
  public CookieNotFoundException(String s) {
    super(s);
  }
}
