package com.baeldung.googlehttpclientguide;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;


public class DailyMotionUrl extends GenericUrl {

    public DailyMotionUrl(String encodedUrl) {
        super(encodedUrl);
    }

    @Key
    public String fields;
}
