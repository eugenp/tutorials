package com.baeldung.web.util;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class HTTPLinkHeaderUtil {

    private HTTPLinkHeaderUtil() {
        throw new AssertionError();
    }

    //

    /**
     * ex. <br>
     * https://api.github.com/users/steveklabnik/gists?page=2>; rel="next", <https://api.github.com/users/steveklabnik/gists?page=3>; rel="last"
     */
    public static List<String> extractAllURIs(final String linkHeader) {
        Preconditions.checkNotNull(linkHeader);

        final List<String> linkHeaders = Lists.newArrayList();
        final String[] links = linkHeader.split(", ");
        for (final String link : links) {
            final int positionOfSeparator = link.indexOf(';');
            linkHeaders.add(link.substring(1, positionOfSeparator - 1));
        }

        return linkHeaders;
    }

    public static String extractURIByRel(final String linkHeader, final String rel) {
        if (linkHeader == null) {
            return null;
        }

        String uriWithSpecifiedRel = null;
        final String[] links = linkHeader.split(", ");
        String linkRelation = null;
        for (final String link : links) {
            final int positionOfSeparator = link.indexOf(';');
            linkRelation = link.substring(positionOfSeparator + 1, link.length()).trim();
            if (extractTypeOfRelation(linkRelation).equals(rel)) {
                uriWithSpecifiedRel = link.substring(1, positionOfSeparator - 1);
                break;
            }
        }

        return uriWithSpecifiedRel;
    }

    static Object extractTypeOfRelation(final String linkRelation) {
        final int positionOfEquals = linkRelation.indexOf('=');
        return linkRelation.substring(positionOfEquals + 2, linkRelation.length() - 1).trim();
    }

    /**
     * ex. <br>
     * https://api.github.com/users/steveklabnik/gists?page=2>; rel="next"
     */
    public static String extractSingleURI(final String linkHeader) {
        Preconditions.checkNotNull(linkHeader);
        final int positionOfSeparator = linkHeader.indexOf(';');

        return linkHeader.substring(1, positionOfSeparator - 1);
    }

}
