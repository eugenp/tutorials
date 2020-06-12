package com.baeldung.hexagonal.domain;

import java.net.MalformedURLException;
import java.net.URL;

public class ShortenedUrl {

    private String code;
    private String url;

    public ShortenedUrl(String code, String url) {
        validate(url);
        this.code = code;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    void validate(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new ShortenerException(ErrorTypeEnum.MALFORMED_URL);
        }
    }

}
