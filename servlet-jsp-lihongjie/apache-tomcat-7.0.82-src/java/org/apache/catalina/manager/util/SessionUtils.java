/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.manager.util;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.security.auth.Subject;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.apache.tomcat.util.ExceptionUtils;

/**
 * Utility methods on HttpSessions...
 * @author C&eacute;drik LIME
 */
public class SessionUtils {

    /**
     *
     */
    private SessionUtils() {
        super();
    }

    /**
     * The session attributes key under which the user's selected
     * <code>java.util.Locale</code> is stored, if any.
     */
    // org.apache.struts.Globals.LOCALE_KEY
    private static final String STRUTS_LOCALE_KEY = "org.apache.struts.action.LOCALE";//$NON-NLS-1$
    // javax.servlet.jsp.jstl.core.Config.FMT_LOCALE
    private static final String JSTL_LOCALE_KEY   = "javax.servlet.jsp.jstl.fmt.locale";//$NON-NLS-1$
    // org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME
    private static final String SPRING_LOCALE_KEY = "org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE";//$NON-NLS-1$
    /**
     * Lower and upper-case strings will be dynamically generated. Put mid-capitalised strings here!
     */
    private static final String[] LOCALE_TEST_ATTRIBUTES = new String[] {
        STRUTS_LOCALE_KEY, SPRING_LOCALE_KEY, JSTL_LOCALE_KEY, "Locale", "java.util.Locale" };
    /**
     * For efficient operation, list the attributes here with the typically used
     * capitalisation. This will be tried first and then the auto-generated
     * upper and lower case versions will be tried.
     */
    private static final String[] USER_TEST_ATTRIBUTES = new String[] {
        "Login", "User", "userName", "UserName", "Utilisateur",
        "SPRING_SECURITY_LAST_USERNAME"};

    /**
     * Try to get user locale from the session, if possible.
     * IMPLEMENTATION NOTE: this method has explicit support for Tapestry 3, Struts 1.x and Spring
     * JSF check the browser meta tag "accept languages" to choose what language to display.
     * @param in_session
     * @return String
     */
    public static Locale guessLocaleFromSession(final Session in_session) {
        return guessLocaleFromSession(in_session.getSession());
    }
    public static Locale guessLocaleFromSession(final HttpSession in_session) {
        if (null == in_session) {
            return null;
        }
        try {
            Locale locale = null;

            // First search "known locations"
            for (int i = 0; i < LOCALE_TEST_ATTRIBUTES.length; ++i) {
                Object obj = in_session.getAttribute(LOCALE_TEST_ATTRIBUTES[i]);
                if (null != obj && obj instanceof Locale) {
                    locale = (Locale) obj;
                    break;
                }
                obj = in_session.getAttribute(LOCALE_TEST_ATTRIBUTES[i].toLowerCase(Locale.ENGLISH));
                if (null != obj && obj instanceof Locale) {
                    locale = (Locale) obj;
                    break;
                }
                obj = in_session.getAttribute(LOCALE_TEST_ATTRIBUTES[i].toUpperCase(Locale.ENGLISH));
                if (null != obj && obj instanceof Locale) {
                    locale = (Locale) obj;
                    break;
                }
            }

            if (null != locale) {
                return locale;
            }

            // Tapestry 3.0: Engine stored in session under "org.apache.tapestry.engine:" + config.getServletName()
            // TODO: Tapestry 4+
            {
                final List<Object> tapestryArray = new ArrayList<Object>();
                for (Enumeration<String> enumeration = in_session.getAttributeNames(); enumeration.hasMoreElements();) {
                    String name = enumeration.nextElement();
                    if (name.indexOf("tapestry") > -1 && name.indexOf("engine") > -1 && null != in_session.getAttribute(name)) {//$NON-NLS-1$ //$NON-NLS-2$
                        tapestryArray.add(in_session.getAttribute(name));
                    }
                }
                if (tapestryArray.size() == 1) {
                    // found a potential Engine! Let's call getLocale() on it.
                    Object probableEngine = tapestryArray.get(0);
                    if (null != probableEngine) {
                        try {
                            Method readMethod = probableEngine.getClass().getMethod("getLocale", (Class<?>[])null);//$NON-NLS-1$
                            // Call the property getter and return the value
                            Object possibleLocale = readMethod.invoke(probableEngine, (Object[]) null);
                            if (null != possibleLocale && possibleLocale instanceof Locale) {
                                locale = (Locale) possibleLocale;
                            }
                        } catch (Exception e) {
                            Throwable t = ExceptionUtils
                                    .unwrapInvocationTargetException(e);
                            ExceptionUtils.handleThrowable(t);
                            // stay silent
                        }
                    }
                }
            }

            if (null != locale) {
                return locale;
            }

            // Last guess: iterate over all attributes, to find a Locale
            // If there is only one, consider it to be /the/ locale
            {
                final List<Object> localeArray = new ArrayList<Object>();
                for (Enumeration<String> enumeration = in_session.getAttributeNames(); enumeration.hasMoreElements();) {
                    String name = enumeration.nextElement();
                    Object obj = in_session.getAttribute(name);
                    if (null != obj && obj instanceof Locale) {
                        localeArray.add(obj);
                    }
                }
                if (localeArray.size() == 1) {
                    locale = (Locale) localeArray.get(0);
                }
            }

            return locale;
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return null;
        }
    }

    /**
     * Try to get user from the session, if possible.
     * @param in_session
     * @return Object
     */
    public static Object guessUserFromSession(final Session in_session) {
        if (null == in_session) {
            return null;
        }
        if (in_session.getPrincipal() != null) {
            return in_session.getPrincipal().getName();
        }
        HttpSession httpSession = in_session.getSession();
        if (httpSession == null)
            return null;
        
        try {
            Object user = null;
            // First search "known locations"
            for (int i = 0; i < USER_TEST_ATTRIBUTES.length; ++i) {
                Object obj = httpSession.getAttribute(USER_TEST_ATTRIBUTES[i]);
                if (null != obj) {
                    user = obj;
                    break;
                }
                obj = httpSession.getAttribute(USER_TEST_ATTRIBUTES[i].toLowerCase(Locale.ENGLISH));
                if (null != obj) {
                    user = obj;
                    break;
                }
                obj = httpSession.getAttribute(USER_TEST_ATTRIBUTES[i].toUpperCase(Locale.ENGLISH));
                if (null != obj) {
                    user = obj;
                    break;
                }
            }

            if (null != user) {
                return user;
            }

            // Last guess: iterate over all attributes, to find a java.security.Principal or javax.security.auth.Subject
            // If there is only one, consider it to be /the/ user
            {
                final List<Object> principalArray = new ArrayList<Object>();
                for (Enumeration<String> enumeration = httpSession.getAttributeNames(); enumeration.hasMoreElements();) {
                    String name = enumeration.nextElement();
                    Object obj = httpSession.getAttribute(name);
                    if (null != obj && (obj instanceof Principal || obj instanceof Subject)) {
                        principalArray.add(obj);
                    }
                }
                if (principalArray.size() == 1) {
                    user = principalArray.get(0);
                }
            }

            if (null != user) {
                return user;
            }

            return user;
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return null;
        }
    }


    public static long getUsedTimeForSession(Session in_session) {
        try {
            long diffMilliSeconds = in_session.getThisAccessedTime() - in_session.getCreationTime();
            return diffMilliSeconds;
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return -1;
        }
    }

    public static long getTTLForSession(Session in_session) {
        try {
            long diffMilliSeconds = (1000*in_session.getMaxInactiveInterval()) - (System.currentTimeMillis() - in_session.getThisAccessedTime());
            return diffMilliSeconds;
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return -1;
        }
    }

    public static long getInactiveTimeForSession(Session in_session) {
        try {
            long diffMilliSeconds =  System.currentTimeMillis() - in_session.getThisAccessedTime();
            return diffMilliSeconds;
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return -1;
        }
    }
}
