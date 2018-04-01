// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;
import java.util.*;

import com.oreilly.servlet.LocaleToCharsetMap;

/** 
 * A class to aid in servlet internationalization.  It determines, from a 
 * client request, the best charset, locale, and resource bundle to use 
 * with the response.
 * <p>
 * LocaleNegotiator works by scanning through the client's language 
 * preferences (sent by browsers in the <tt>Accept-Language</tt> header) 
 * looking for any 
 * language for which there exists is a corresponding resource bundle.
 * When it finds a correspondence, it uses the LocaleToCharsetMap class
 * to determine the charset.  If there's any problem, it tries to fall 
 * back to US English.  The logic currently ignores the client's charset 
 * preferences (sent in the <tt>Accept-Charset</tt> header).
 * <p>
 * It can be used like this:
 * <blockquote><pre>
 * String bundleName = "BundleName";
 * String acceptLanguage = req.getHeader("Accept-Language");
 * String acceptCharset = req.getHeader("Accept-Charset");
 * &nbsp;
 * LocaleNegotiator negotiator =
 *   new LocaleNegotiator(bundleName, acceptLanguage, acceptCharset);
 * &nbsp;
 * Locale locale = negotiator.getLocale();
 * String charset = negotiator.getCharset();
 * ResourceBundle bundle = negotiator.getBundle();  // may be null
 * &nbsp;
 * res.setContentType("text/plain; charset=" + charset);
 * res.setHeader("Content-Language", locale.getLanguage());
 * res.setHeader("Vary", "Accept-Language");
 * &nbsp;
 * PrintWriter out = res.getWriter();
 * &nbsp;
 * out.println(bundle.getString("resource"));
 * </pre></blockquote>
 *
 * @see com.oreilly.servlet.LocaleToCharsetMap
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 1998
 * @version 1.0, 98/09/18
 */
public class LocaleNegotiator {

  private ResourceBundle chosenBundle; 
  private Locale chosenLocale; 
  private String chosenCharset; 

  /**
   * Constructs a new LocaleNegotiator for the given bundle name, language
   * list, and charset list.
   *
   * @param bundleName the resource bundle name
   * @param languages the Accept-Language header
   * @param charsets the Accept-Charset header
   */
  public LocaleNegotiator(String bundleName,
                          String languages,
                          String charsets) {

    // Specify default values:
    //   English language, ISO-8859-1 (Latin-1) charset, English bundle
    Locale defaultLocale = new Locale("en", "US");
    String defaultCharset = "ISO-8859-1";
    ResourceBundle defaultBundle = null;
    try {
      defaultBundle = ResourceBundle.getBundle(bundleName, defaultLocale);
    }
    catch (MissingResourceException e) {
      // No default bundle was found.  Flying without a net.
    }

    // If the client didn't specify acceptable languages, we can keep
    // the defaults.
    if (languages == null) {
      chosenLocale = defaultLocale;
      chosenCharset = defaultCharset;
      chosenBundle = defaultBundle;
      return;  // quick exit
    }

    // Use a tokenizer to separate acceptable languages
    StringTokenizer tokenizer = new StringTokenizer(languages, ",");

    while (tokenizer.hasMoreTokens()) {
      // Get the next acceptable language.
      // (The language can look something like "en; qvalue=0.91")
      String lang = tokenizer.nextToken();

      // Get the locale for that language
      Locale loc = getLocaleForLanguage(lang);

      // Get the bundle for this locale.  Don't let the search fallback 
      // to match other languages!
      ResourceBundle bundle = getBundleNoFallback(bundleName, loc);

      // The returned bundle is null if there's no match.  In that case
      // we can't use this language since the servlet can't speak it.
      if (bundle == null) continue;  // on to the next language

      // Find a charset we can use to display that locale's language.
      String charset = getCharsetForLocale(loc, charsets);

      // The returned charset is null if there's no match.  In that case
      // we can't use this language since the servlet can't encode it.
      if (charset == null) continue;  // on to the next language

      // If we get here, there are no problems with this language.
      chosenLocale = loc;
      chosenBundle = bundle;
      chosenCharset = charset;
      return;  // we're done
    }

    // No matches, so we let the defaults stand
    chosenLocale = defaultLocale;
    chosenCharset = defaultCharset;
    chosenBundle = defaultBundle;
  }

  /**
   * Gets the chosen bundle.
   *
   * @return the chosen bundle
   */
  public ResourceBundle getBundle() {
    return chosenBundle;
  }

  /**
   * Gets the chosen locale.
   *
   * @return the chosen locale
   */
  public Locale getLocale() {
    return chosenLocale;
  }

  /**
   * Gets the chosen charset.
   *
   * @return the chosen charset
   */
  public String getCharset() {
    return chosenCharset;
  }

  /*
   * Gets a Locale object for a given language string
   */
  private Locale getLocaleForLanguage(String lang) {
    Locale loc;
    int semi, dash;

    // Cut off any qvalue that might come after a semi-colon
    if ((semi = lang.indexOf(';')) != -1) {
      lang = lang.substring(0, semi);
    }

    // Trim any whitespace
    lang = lang.trim();

    // Create a Locale from the language.  A dash may separate the
    // language from the country.
    if ((dash = lang.indexOf('-')) == -1) {
      loc = new Locale(lang, "");  // No dash, no country
    }
    else {
      loc = new Locale(lang.substring(0, dash), lang.substring(dash+1));
    }

    return loc;
  }

  /*
   * Gets a ResourceBundle object for the given bundle name and locale,
   * or null if the bundle can't be found.  The resource bundle must match
   * the locale exactly.  Fallback matches are not permitted.
   */
  private ResourceBundle getBundleNoFallback(String bundleName, Locale loc) {

    // First get the fallback bundle -- the bundle that will be selected 
    // if getBundle() can't find a direct match.  This bundle can be
    // compared to the bundles returned by later calls to getBundle() in
    // order to detect when getBundle() finds a direct match.
    ResourceBundle fallback = null;
    try {
      fallback =
        ResourceBundle.getBundle(bundleName, new Locale("bogus", ""));
    }
    catch (MissingResourceException e) {
      // No fallback bundle was found.
    }

    try {
      // Get the bundle for the specified locale
      ResourceBundle bundle = ResourceBundle.getBundle(bundleName, loc);

      // Is the bundle different than our fallback bundle?
      if (bundle != fallback) {
        // We have a real match!
        return bundle;
      }
      // So the bundle is the same as our fallback bundle.
      // We can still have a match, but only if our locale's language 
      // matches the default locale's language.
      else if (bundle == fallback &&
            loc.getLanguage().equals(Locale.getDefault().getLanguage())) {
        // Another way to match
        return bundle;
      }
      else {
        // No match, keep looking
      }
    }
    catch (MissingResourceException e) {
      // No bundle available for this locale
    }

    return null;  // no match
  }

  /**
   * Gets the best charset for a given locale, selecting from a charset list.
   * Currently ignores the charset list.  Subclasses can override this 
   * method to take the list into account.
   *
   * @param loc the locale
   * @param charsets a comma-separated charset list
   * @return the best charset for the given locale from the given list
   */
  protected String getCharsetForLocale(Locale loc, String charsets) {
    // Note: This method ignores the client-specified charsets
    return LocaleToCharsetMap.getCharset(loc);
  }
}
