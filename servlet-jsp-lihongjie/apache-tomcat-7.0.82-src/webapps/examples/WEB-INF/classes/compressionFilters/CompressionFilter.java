/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package compressionFilters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of <code>javax.servlet.Filter</code> used to compress
 * the ServletResponse if it is bigger than a threshold.
 *
 * @author Amy Roh
 * @author Dmitri Valdin
 */
public class CompressionFilter implements Filter {

    /**
     * Minimal reasonable threshold.
     */
    private static final int MIN_THRESHOLD = 128;

    /**
     * Minimal reasonable buffer.
     */
    // 8KB is what tomcat would use by default anyway
    private static final int MIN_BUFFER = 8192;

    /**
     * The filter configuration object we are associated with.  If this value
     * is null, this filter instance is not currently configured.
     */
    private FilterConfig config = null;

    /**
     * The threshold number to compress.
     */
    protected int compressionThreshold = 0;

    /**
     * The compression buffer size to avoid chunking.
     */
    protected int compressionBuffer = 0;

    /**
     * The mime types to compress.
     */
    protected String[] compressionMimeTypes = {"text/html", "text/xml", "text/plain"};

    /**
     * Debug level for this filter.
     */
    private int debug = 0;

    /**
     * Place this filter into service.
     *
     * @param filterConfig The filter configuration object
     */
    @Override
    public void init(FilterConfig filterConfig) {

        config = filterConfig;
        if (filterConfig != null) {
            String value = filterConfig.getInitParameter("debug");
            if (value!=null) {
                debug = Integer.parseInt(value);
            }

            String str = filterConfig.getInitParameter("compressionThreshold");
            if (str!=null) {
                compressionThreshold = Integer.parseInt(str);
                if (compressionThreshold != 0 && compressionThreshold < MIN_THRESHOLD) {
                    if (debug > 0) {
                        System.out.println("compressionThreshold should be either 0 - no compression or >= " + MIN_THRESHOLD);
                        System.out.println("compressionThreshold set to " + MIN_THRESHOLD);
                    }
                    compressionThreshold = MIN_THRESHOLD;
                }
            }

            str = filterConfig.getInitParameter("compressionBuffer");
            if (str!=null) {
                compressionBuffer = Integer.parseInt(str);
                if (compressionBuffer < MIN_BUFFER) {
                    if (debug > 0) {
                        System.out.println("compressionBuffer should be >= " + MIN_BUFFER);
                        System.out.println("compressionBuffer set to " + MIN_BUFFER);
                    }
                    compressionBuffer = MIN_BUFFER;
                }
            }

            str = filterConfig.getInitParameter("compressionMimeTypes");
            if (str!=null) {
                List<String> values = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(str, ",");

                while (st.hasMoreTokens()) {
                    String token = st.nextToken().trim();
                    if (token.length() > 0) {
                        values.add(token);
                    }
                }

                if (values.size() > 0) {
                    compressionMimeTypes = values.toArray(
                            new String[values.size()]);
                } else {
                    compressionMimeTypes = null;
                }

                if (debug > 0) {
                    System.out.println("compressionMimeTypes set to " +
                            Arrays.toString(compressionMimeTypes));
                }
            }
        }

    }

    /**
    * Take this filter out of service.
    */
    @Override
    public void destroy() {

        this.config = null;

    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due
     * to a client request for a resource at the end of the chain.
     * The FilterChain passed into this method allows the Filter to pass on the
     * request and response to the next entity in the chain.<p>
     * This method first examines the request to check whether the client support
     * compression. <br>
     * It simply just pass the request and response if there is no support for
     * compression.<br>
     * If the compression support is available, it creates a
     * CompressionServletResponseWrapper object which compresses the content and
     * modifies the header if the content length is big enough.
     * It then invokes the next entity in the chain using the FilterChain object
     * (<code>chain.doFilter()</code>), <br>
     **/
    @Override
    public void doFilter ( ServletRequest request, ServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {

        if (debug > 0) {
            System.out.println("@doFilter");
        }

        if (compressionThreshold == 0) {
            if (debug > 0) {
                System.out.println("doFilter got called, but compressionThreshold is set to 0 - no compression");
            }
            chain.doFilter(request, response);
            return;
        }

        boolean supportCompression = false;
        if (request instanceof HttpServletRequest) {
            if (debug > 1) {
                System.out.println("requestURI = " + ((HttpServletRequest)request).getRequestURI());
            }

            // Are we allowed to compress ?
            String s = ((HttpServletRequest)request).getParameter("gzip");
            if ("false".equals(s)) {
                if (debug > 0) {
                    System.out.println("got parameter gzip=false --> don't compress, just chain filter");
                }
                chain.doFilter(request, response);
                return;
            }

            Enumeration<String> e =
                ((HttpServletRequest)request).getHeaders("Accept-Encoding");
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                if (name.indexOf("gzip") != -1) {
                    if (debug > 0) {
                        System.out.println("supports compression");
                    }
                    supportCompression = true;
                } else {
                    if (debug > 0) {
                        System.out.println("no support for compression");
                    }
                }
            }
        }

        if (supportCompression) {
            if (response instanceof HttpServletResponse) {
                CompressionServletResponseWrapper wrappedResponse =
                    new CompressionServletResponseWrapper((HttpServletResponse)response);
                wrappedResponse.setDebugLevel(debug);
                wrappedResponse.setCompressionThreshold(compressionThreshold);
                wrappedResponse.setCompressionBuffer(compressionBuffer);
                wrappedResponse.setCompressionMimeTypes(compressionMimeTypes);
                if (debug > 0) {
                    System.out.println("doFilter gets called with compression");
                }
                try {
                    chain.doFilter(request, wrappedResponse);
                } finally {
                    wrappedResponse.finishResponse();
                }
                return;
            }
        } else {
            if (debug > 0) {
                System.out.println("doFilter gets called w/o compression");
            }
            chain.doFilter(request, response);
            return;
        }
    }

    /**
     * Set filter config
     * This function is equivalent to init. Required by Weblogic 6.1
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        init(filterConfig);
    }

    /**
     * Required by Weblogic 6.1
     *
     * @return the FilterConfig that was used to initialise this filter.
     */
    public FilterConfig getFilterConfig() {
        return config;
    }
}

