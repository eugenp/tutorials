package com.baeldung.googlehttpclientguide;

import com.google.api.client.util.Key;
import java.util.List;

public class VideoFeed {

    @Key
    public List<Video> list;

    @Key("has_more")
    public boolean hasMore;
}
