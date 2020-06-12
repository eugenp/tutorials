package com.baeldung.hexagonal.domain.service;

public interface ShortenedUrlService {

    String create(String url);

    String get(String code);

}
