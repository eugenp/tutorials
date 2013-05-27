package org.baeldung.web.controller;

import javax.servlet.http.HttpServletResponse;

/**
 * Provides some constants and utility methods to build a Link Header to be stored in the {@link HttpServletResponse} object
 */
public final class LinkUtil {

    private LinkUtil() {
        throw new AssertionError();
    }

    //

    /**
     * Creates a Link Header to be stored in the {@link HttpServletResponse} to provide Discoverability features to the user
     * 
     * @param uri
     *            the base uri
     * @param rel
     *            the relative path
     * 
     * @return the complete url
     */
    public static String createLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }

}
