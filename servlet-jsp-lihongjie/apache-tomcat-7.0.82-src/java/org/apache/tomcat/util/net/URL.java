/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.util.net;


import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Locale;


/**
 * <p><strong>URL</strong> is designed to provide public APIs for parsing
 * and synthesizing Uniform Resource Locators as similar as possible to the
 * APIs of <code>java.net.URL</code>, but without the ability to open a
 * stream or connection.  One of the consequences of this is that you can
 * construct URLs for protocols for which a URLStreamHandler is not
 * available (such as an "https" URL when JSSE is not installed).</p>
 *
 * <p><strong>WARNING</strong> - This class assumes that the string
 * representation of a URL conforms to the <code>spec</code> argument
 * as described in RFC 2396 "Uniform Resource Identifiers: Generic Syntax":
 * <pre>
 *   &lt;scheme&gt;//&lt;authority&gt;&lt;path&gt;?&lt;query&gt;#&lt;fragment&gt;
 * </pre></p>
 *
 * <p><strong>FIXME</strong> - This class really ought to end up in a Commons
 * package someplace.</p>
 *
 * @author Craig R. McClanahan
 */
public final class URL implements Serializable {

    private static final long serialVersionUID = 1L;


    // ----------------------------------------------------------- Constructors

    /**
     * Create a URL object from the specified String representation.
     *
     * @param spec String representation of the URL
     *
     * @exception MalformedURLException if the string representation
     *  cannot be parsed successfully
     */
    public URL(String spec) throws MalformedURLException {

        this(null, spec);

    }


    /**
     * Create a URL object by parsing a string representation relative
     * to a specified context.  Based on logic from JDK 1.3.1's
     * <code>java.net.URL</code>.
     *
     * @param context URL against which the relative representation
     *  is resolved
     * @param spec String representation of the URL (usually relative)
     *
     * @exception MalformedURLException if the string representation
     *  cannot be parsed successfully
     */
    public URL(URL context, String spec) throws MalformedURLException {

        String original = spec;
        int i, limit, c;
        int start = 0;
        String newProtocol = null;
        boolean aRef = false;

        try {

            // Eliminate leading and trailing whitespace
            limit = spec.length();
            while ((limit > 0) && (spec.charAt(limit - 1) <= ' ')) {
                limit--;
            }
            while ((start < limit) && (spec.charAt(start) <= ' ')) {
                start++;
            }

            // If the string representation starts with "url:", skip it
            if (spec.regionMatches(true, start, "url:", 0, 4)) {
                start += 4;
            }

            // Is this a ref relative to the context URL?
            if ((start < spec.length()) && (spec.charAt(start) == '#')) {
                aRef = true;
            }

            // Parse out the new protocol
            for (i = start; !aRef && (i < limit) ; i++) {
                c = spec.charAt(i);
                if (c == ':') {
                    String s = spec.substring(start, i).toLowerCase(Locale.ENGLISH);
                    // Assume all protocols are valid
                    newProtocol = s;
                    start = i + 1;
                    break;
                } else if( c == '#' ) {
                    aRef = true;
                } else if( !isSchemeChar((char)c) ) {
                    break;
                }
            }

            // Only use our context if the protocols match
            protocol = newProtocol;
            if ((context != null) && ((newProtocol == null) ||
                 newProtocol.equalsIgnoreCase(context.getProtocol()))) {
                // If the context is a hierarchical URL scheme and the spec
                // contains a matching scheme then maintain backwards
                // compatibility and treat it as if the spec didn't contain
                // the scheme; see 5.2.3 of RFC2396
                if ((context.getPath() != null) &&
                    (context.getPath().startsWith("/")))
                    newProtocol = null;
                if (newProtocol == null) {
                    protocol = context.getProtocol();
                    authority = context.getAuthority();
                    userInfo = context.getUserInfo();
                    host = context.getHost();
                    port = context.getPort();
                    file = context.getFile();
                    int question = file.lastIndexOf('?');
                    if (question < 0)
                        path = file;
                    else
                        path = file.substring(0, question);
                }
            }

            if (protocol == null)
                throw new MalformedURLException("no protocol: " + original);

            // Parse out any ref portion of the spec
            i = spec.indexOf('#', start);
            if (i >= 0) {
                ref = spec.substring(i + 1, limit);
                limit = i;
            }

            // Parse the remainder of the spec in a protocol-specific fashion
            parse(spec, start, limit);
            if (context != null)
                normalize();


        } catch (MalformedURLException e) {
            throw e;
        } catch (Exception e) {
            throw new MalformedURLException(e.toString());
        }

    }


    /**
     * Create a URL object from the specified components.  The default port
     * number for the specified protocol will be used.
     *
     * @param protocol Name of the protocol to use
     * @param host Name of the host addressed by this protocol
     * @param file Filename on the specified host
     *
     * @exception MalformedURLException is never thrown, but present for
     *  compatible APIs
     *
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public URL(String protocol, String host, String file)
        throws MalformedURLException {

        this(protocol, host, -1, file);

    }


    /**
     * Create a URL object from the specified components.  Specifying a port
     * number of -1 indicates that the URL should use the default port for
     * that protocol.  Based on logic from JDK 1.3.1's
     * <code>java.net.URL</code>.
     *
     * @param protocol Name of the protocol to use
     * @param host Name of the host addressed by this protocol
     * @param port Port number, or -1 for the default port for this protocol
     * @param file Filename on the specified host
     *
     * @exception MalformedURLException is never thrown, but present for
     *  compatible APIs
     *
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public URL(String protocol, String host, int port, String file)
        throws MalformedURLException {

        this.protocol = protocol;
        this.host = host;
        this.port = port;

        int hash = file.indexOf('#');
        this.file = hash < 0 ? file : file.substring(0, hash);
        this.ref = hash < 0 ? null : file.substring(hash + 1);
        int question = file.lastIndexOf('?');
        if (question >= 0) {
            query = file.substring(question + 1);
            path = file.substring(0, question);
        } else
            path = file;

        if ((host != null) && (host.length() > 0))
            authority = (port == -1) ? host : host + ":" + port;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The authority part of the URL.
     */
    private String authority = null;


    /**
     * The filename part of the URL.
     */
    private String file = null;


    /**
     * The host name part of the URL.
     */
    private String host = null;


    /**
     * The path part of the URL.
     */
    private String path = null;


    /**
     * The port number part of the URL.
     */
    private int port = -1;


    /**
     * The protocol name part of the URL.
     */
    private String protocol = null;


    /**
     * The query part of the URL.
     */
    private String query = null;


    /**
     * The reference part of the URL.
     */
    private String ref = null;


    /**
     * The user info part of the URL.
     */
    private String userInfo = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Compare two URLs for equality.  The result is <code>true</code> if and
     * only if the argument is not null, and is a <code>URL</code> object
     * that represents the same <code>URL</code> as this object.  Two
     * <code>URLs</code> are equal if they have the same protocol and
     * reference the same host, the same port number on the host,
     * and the same file and anchor on the host.
     *
     * @param obj The URL to compare against
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof URL))
            return (false);
        URL other = (URL) obj;
        if (!sameFile(other))
            return (false);
        return (compare(ref, other.getRef()));

    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        result = prime * result +
                ((protocol == null) ? 0 : protocol.hashCode());
        result = prime * result + ((ref == null) ? 0 : ref.hashCode());
        return result;
    }


    /**
     * Return the authority part of the URL.
     */
    public String getAuthority() {

        return (this.authority);

    }


    /**
     * Return the filename part of the URL.  <strong>NOTE</strong> - For
     * compatibility with <code>java.net.URL</code>, this value includes
     * the query string if there was one.  For just the path portion,
     * call <code>getPath()</code> instead.
     */
    public String getFile() {

        if (file == null)
            return ("");
        return (this.file);

    }


    /**
     * Return the host name part of the URL.
     */
    public String getHost() {

        return (this.host);

    }


    /**
     * Return the path part of the URL.
     */
    public String getPath() {

        if (this.path == null)
            return ("");
        return (this.path);

    }


    /**
     * Return the port number part of the URL.
     */
    public int getPort() {

        return (this.port);

    }


    /**
     * Return the protocol name part of the URL.
     */
    public String getProtocol() {

        return (this.protocol);

    }


    /**
     * Return the query part of the URL.
     */
    public String getQuery() {

        return (this.query);

    }


    /**
     * Return the reference part of the URL.
     */
    public String getRef() {

        return (this.ref);

    }


    /**
     * Return the user info part of the URL.
     */
    public String getUserInfo() {

        return (this.userInfo);

    }


    /**
     * Normalize the <code>path</code> (and therefore <code>file</code>)
     * portions of this URL.
     * <p>
     * <strong>NOTE</strong> - This method is not part of the public API
     * of <code>java.net.URL</code>, but is provided as a value added
     * service of this implementation.
     *
     * @exception MalformedURLException if a normalization error occurs,
     *  such as trying to move about the hierarchical root
     */
    public void normalize() throws MalformedURLException {

        // Special case for null path
        if (path == null) {
            if (query != null)
                file = "?" + query;
            else
                file = "";
            return;
        }

        // Create a place for the normalized path
        String normalized = path;
        if (normalized.equals("/.")) {
            path = "/";
            if (query != null)
                file = path + "?" + query;
            else
                file = path;
            return;
        }

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                throw new MalformedURLException
                    ("Invalid relative URL reference");
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                normalized.substring(index + 3);
        }

        // Resolve occurrences of "/." at the end of the normalized path
        if (normalized.endsWith("/."))
            normalized = normalized.substring(0, normalized.length() - 1);

        // Resolve occurrences of "/.." at the end of the normalized path
        if (normalized.endsWith("/..")) {
            int index = normalized.length() - 3;
            int index2 = normalized.lastIndexOf('/', index - 1);
            if (index2 < 0)
                throw new MalformedURLException
                    ("Invalid relative URL reference");
            normalized = normalized.substring(0, index2 + 1);
        }

        // Return the normalized path that we have completed
        path = normalized;
        if (query != null)
            file = path + "?" + query;
        else
            file = path;

    }


    /**
     * Compare two URLs, excluding the "ref" fields.  Returns <code>true</code>
     * if this <code>URL</code> and the <code>other</code> argument both refer
     * to the same resource.  The two <code>URLs</code> might not both contain
     * the same anchor.
     */
    public boolean sameFile(URL other) {

        if (!compare(protocol, other.getProtocol()))
            return (false);
        if (!compare(host, other.getHost()))
            return (false);
        if (port != other.getPort())
            return (false);
        if (!compare(file, other.getFile()))
            return (false);
        return (true);

    }


    /**
     * Return a string representation of this URL.  This follow the rules in
     * RFC 2396, Section 5.2, Step 7.
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public String toExternalForm() {

        StringBuilder sb = new StringBuilder();
        if (protocol != null) {
            sb.append(protocol);
            sb.append(":");
        }
        if (authority != null) {
            sb.append("//");
            sb.append(authority);
        }
        if (path != null)
            sb.append(path);
        if (query != null) {
            sb.append('?');
            sb.append(query);
        }
        if (ref != null) {
            sb.append('#');
            sb.append(ref);
        }
        return (sb.toString());

    }


    /**
     * Return a string representation of this object.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("URL[");
        sb.append("authority=");
        sb.append(authority);
        sb.append(", file=");
        sb.append(file);
        sb.append(", host=");
        sb.append(host);
        sb.append(", port=");
        sb.append(port);
        sb.append(", protocol=");
        sb.append(protocol);
        sb.append(", query=");
        sb.append(query);
        sb.append(", ref=");
        sb.append(ref);
        sb.append(", userInfo=");
        sb.append(userInfo);
        sb.append("]");
        return (sb.toString());

        //        return (toExternalForm());

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Compare to String values for equality, taking appropriate care if one
     * or both of the values are <code>null</code>.
     *
     * @param first First string
     * @param second Second string
     */
    private boolean compare(String first, String second) {

        if (first == null) {
            if (second == null)
                return (true);
            else
                return (false);
        } else {
            if (second == null)
                return (false);
            else
                return (first.equals(second));
        }

    }


    /**
     * Parse the specified portion of the string representation of a URL,
     * assuming that it has a format similar to that for <code>http</code>.
     *
     * <p><strong>FIXME</strong> - This algorithm can undoubtedly be optimized
     * for performance.  However, that needs to wait until after sufficient
     * unit tests are implemented to guarantee correct behavior with no
     * regressions.</p>
     *
     * @param spec String representation being parsed
     * @param start Starting offset, which will be just after the ':' (if
     *  there is one) that determined the protocol name
     * @param limit Ending position, which will be the position of the '#'
     *  (if there is one) that delimited the anchor
     *
     * @exception MalformedURLException if a parsing error occurs
     */
    private void parse(String spec, int start, int limit)
        throws MalformedURLException {

        // Trim the query string (if any) off the tail end
        int question = spec.lastIndexOf('?', limit - 1);
        if ((question >= 0) && (question < limit)) {
            query = spec.substring(question + 1, limit);
            limit = question;
        } else {
            query = null;
        }

        // Parse the authority section
        if (spec.indexOf("//", start) == start) {
            int pathStart = spec.indexOf('/', start + 2);
            if ((pathStart >= 0) && (pathStart < limit)) {
                authority = spec.substring(start + 2, pathStart);
                start = pathStart;
            } else {
                authority = spec.substring(start + 2, limit);
                start = limit;
            }
            if (authority.length() > 0) {
                int at = authority.indexOf('@');
                if( at >= 0 ) {
                    userInfo = authority.substring(0,at);
                }
                int ipv6 = authority.indexOf('[',at+1);
                int hStart = at+1;
                if( ipv6 >= 0 ) {
                    hStart = ipv6;
                    ipv6 = authority.indexOf(']', ipv6);
                    if( ipv6 < 0 ) {
                        throw new MalformedURLException(
                                                        "Closing ']' not found in IPV6 address: " + authority);
                    } else {
                        at = ipv6-1;
                    }
                }

                int colon = authority.indexOf(':', at+1);
                if (colon >= 0) {
                    try {
                        port =
                            Integer.parseInt(authority.substring(colon + 1));
                    } catch (NumberFormatException e) {
                        throw new MalformedURLException(e.toString());
                    }
                    host = authority.substring(hStart, colon);
                } else {
                    host = authority.substring(hStart);
                    port = -1;
                }
            }
        }

        // Parse the path section
        if (spec.indexOf('/', start) == start) {     // Absolute path
            path = spec.substring(start, limit);
            if (query != null)
                file = path + "?" + query;
            else
                file = path;
            return;
        }

        // Resolve relative path against our context's file
        if (path == null) {
            if (query != null)
                file = "?" + query;
            else
                file = null;
            return;
        }
        if (!path.startsWith("/"))
            throw new MalformedURLException
                ("Base path does not start with '/'");
        if (!path.endsWith("/"))
            path += "/../";
        path += spec.substring(start, limit);
        if (query != null)
            file = path + "?" + query;
        else
            file = path;
        return;

    }

    /**
     * Determine if the character is allowed in the scheme of a URI.
     * See RFC 2396, Section 3.1
     */
    public static boolean isSchemeChar(char c) {
        return Character.isLetterOrDigit(c) ||
            c == '+' || c == '-' || c == '.';
    }

}
