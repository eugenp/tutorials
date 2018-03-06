package jsp;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  * This class is a filter that, when mapped to "*.jsp", converts
 *  * a URL in the form "pageName.jsp" to "layout.jsp?page=pageName"
 *  * and forwards to the resource at the converted URI.
 *  *
 *  * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 *  * @version 1.0
 *  
 */
public class URLConversionFilter implements Filter {


    private static final String LAYOUT_PAGE = "/layout.jsp";

    private static final String PAGE_PARAM = "page";

    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    /**
     *      * Converts the request URL and forwards to the real resource URL.
     *     
     */

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String currURI = httpReq.getServletPath();
        // Strip off the leading slash
        currURI = currURI.substring(1);
        String pagePath = currURI.substring(0, currURI.lastIndexOf(".jsp"));
        String queryString = httpReq.getQueryString();
        StringBuffer realURI = new StringBuffer(LAYOUT_PAGE);
        realURI.append("?").append(PAGE_PARAM).append("=").
                append(URLEncoder.encode(pagePath));
        if (queryString != null) {
            realURI.append("&").append(queryString);
        }

        ServletContext context = config.getServletContext();
        RequestDispatcher rd =
                context.getRequestDispatcher(realURI.toString());
        if (rd == null) {
            httpResp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Layout page doesn't exist");
        }
        rd.forward(request, response);
        return;
    }


    public void destroy() {
    }
}
