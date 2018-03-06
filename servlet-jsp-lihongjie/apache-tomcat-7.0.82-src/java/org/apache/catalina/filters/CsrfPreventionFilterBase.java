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
package org.apache.catalina.filters;

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public abstract class CsrfPreventionFilterBase extends FilterBase {

    private static final Log log = LogFactory.getLog(CsrfPreventionFilterBase.class);

    private String randomClass = SecureRandom.class.getName();

    private Random randomSource;

    private int denyStatus = HttpServletResponse.SC_FORBIDDEN;

    @Override
    protected Log getLogger() {
        return log;
    }

    /**
     * Return response status code that is used to reject denied request.
     */
    public int getDenyStatus() {
        return denyStatus;
    }

    /**
     * Set response status code that is used to reject denied request. If none
     * set, the default value of 403 will be used.
     *
     * @param denyStatus
     *            HTTP status code
     */
    public void setDenyStatus(int denyStatus) {
        this.denyStatus = denyStatus;
    }

    /**
     * Specify the class to use to generate the nonces. Must be in instance of
     * {@link Random}.
     *
     * @param randomClass
     *            The name of the class to use
     */
    public void setRandomClass(String randomClass) {
        this.randomClass = randomClass;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Set the parameters
        super.init(filterConfig);

        try {
            Class<?> clazz = Class.forName(randomClass);
            randomSource = (Random) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            ServletException se = new ServletException(sm.getString(
                    "csrfPrevention.invalidRandomClass", randomClass), e);
            throw se;
        } catch (InstantiationException e) {
            ServletException se = new ServletException(sm.getString(
                    "csrfPrevention.invalidRandomClass", randomClass), e);
            throw se;
        } catch (IllegalAccessException e) {
            ServletException se = new ServletException(sm.getString(
                    "csrfPrevention.invalidRandomClass", randomClass), e);
            throw se;
        }
    }

    @Override
    protected boolean isConfigProblemFatal() {
        return true;
    }

    /**
     * Generate a once time token (nonce) for authenticating subsequent
     * requests. The nonce generation is a simplified version of
     * ManagerBase.generateSessionId().
     */
    protected String generateNonce() {
        byte random[] = new byte[16];

        // Render the result as a String of hexadecimal digits
        StringBuilder buffer = new StringBuilder();

        randomSource.nextBytes(random);

        for (int j = 0; j < random.length; j++) {
            byte b1 = (byte) ((random[j] & 0xf0) >> 4);
            byte b2 = (byte) (random[j] & 0x0f);
            if (b1 < 10) {
                buffer.append((char) ('0' + b1));
            } else {
                buffer.append((char) ('A' + (b1 - 10)));
            }
            if (b2 < 10) {
                buffer.append((char) ('0' + b2));
            } else {
                buffer.append((char) ('A' + (b2 - 10)));
            }
        }

        return buffer.toString();
    }

    protected String getRequestedPath(HttpServletRequest request) {
        String path = request.getServletPath();
        if (request.getPathInfo() != null) {
            path = path + request.getPathInfo();
        }
        return path;
    }
}
