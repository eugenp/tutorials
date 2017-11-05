package com.baeldung.googlehttpclientguide;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public class Video extends GenericJson {

    @Key
    public String id;

    @Key
    public List<String> tags;

    @Key
    public String title;

    @Key
    public String url;
}
