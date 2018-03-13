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
package org.apache.catalina.authenticator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * An <b>Authenticator</b> and <b>Valve</b> implementation of HTTP BASIC
 * Authentication, as outlined in RFC 2617:  "HTTP Authentication: Basic
 * and Digest Access Authentication."
 *
 * @author Craig R. McClanahan
 */
public class BasicAuthenticator extends AuthenticatorBase {

    /**
     * Descriptive information about this implementation.
     */
    protected static final String info = "org.apache.catalina.authenticator.BasicAuthenticator/1.0";


    private Charset charset = B2CConverter.ISO_8859_1;
    private String charsetString = null;


    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {
        return info;
    }


    public String getCharset() {
        return charsetString;
    }


    public void setCharset(String charsetString) {
        // Only acceptable options are null, "" or "UTF-8" (case insensitive)
        if (charsetString == null || charsetString.isEmpty()) {
            charset = B2CConverter.ISO_8859_1;
        } else if ("UTF-8".equalsIgnoreCase(charsetString)) {
            charset = B2CConverter.UTF_8;
        } else {
            throw new IllegalArgumentException(sm.getString("basicAuthenticator.invalidCharset"));
        }
        this.charsetString = charsetString;
    }


    @Override
    public boolean authenticate(Request request,
                                HttpServletResponse response,
                                LoginConfig config)
        throws IOException {

        if (checkForCachedAuthentication(request, response, true)) {
            return true;
        }

        // Validate any credentials already included with this request
        String username = null;
        String password = null;

        MessageBytes authorization =
            request.getCoyoteRequest().getMimeHeaders()
            .getValue("authorization");

        if (authorization != null) {
            authorization.toBytes();
            ByteChunk authorizationBC = authorization.getByteChunk();
            if (authorizationBC.startsWithIgnoreCase("basic ", 0)) {
                authorizationBC.setOffset(authorizationBC.getOffset() + 6);

                byte[] decoded = Base64.decodeBase64(
                        authorizationBC.getBuffer(),
                        authorizationBC.getOffset(),
                        authorizationBC.getLength());

                // Get username and password
                int colon = -1;
                for (int i = 0; i < decoded.length; i++) {
                    if (decoded[i] == ':') {
                        colon = i;
                        break;
                    }
                }

                if (colon < 0) {
                    username = new String(decoded, charset);
                } else {
                    username = new String(decoded, 0, colon, charset);
                    password = new String(decoded, colon + 1, decoded.length - colon - 1, charset);
                }

                authorizationBC.setOffset(authorizationBC.getOffset() - 6);
            }

            Principal principal = context.getRealm().authenticate(username, password);
            if (principal != null) {
                register(request, response, principal,
                        HttpServletRequest.BASIC_AUTH, username, password);
                return (true);
            }
        }

        StringBuilder value = new StringBuilder(16);
        value.append("Basic realm=\"");
        if (config.getRealmName() == null) {
            value.append(REALM_NAME);
        } else {
            value.append(config.getRealmName());
        }
        value.append('\"');
        if (charsetString != null && !charsetString.isEmpty()) {
            value.append(", charset=");
            value.append(charsetString);
        }
        response.setHeader(AUTH_HEADER_NAME, value.toString());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return (false);

    }


    @Override
    protected String getAuthMethod() {
        return HttpServletRequest.BASIC_AUTH;
    }
}
