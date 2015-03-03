package org.baeldung.reddit.util;

/**
 * Specified by Reddit API at http://www.reddit.com/dev/api#POST_api_submit
 */
public final class RedditApiConstants {
    public static final String API_TYPE = "api_type";
    public static final String URL = "url";
    public static final String SR = "sr";
    public static final String TITLE = "title";
    public static final String THEN = "then";
    public static final String SENDREPLIES = "sendreplies";
    public static final String RESUBMIT = "resubmit";
    public static final String KIND = "kind";

    private RedditApiConstants() {
        throw new AssertionError();
    }
}
