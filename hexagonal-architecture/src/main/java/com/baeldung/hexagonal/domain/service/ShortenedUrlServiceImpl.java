package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.ErrorTypeEnum;
import com.baeldung.hexagonal.domain.ShortenedUrl;
import com.baeldung.hexagonal.domain.ShortenerException;
import com.baeldung.hexagonal.domain.persistence.PersistenceService;
import com.baeldung.hexagonal.domain.shortener.ShortenerService;
import org.apache.commons.lang3.StringUtils;

public class ShortenedUrlServiceImpl implements ShortenedUrlService {

    private ShortenerService shortenerService;
    private PersistenceService persistenceService;

    public ShortenedUrlServiceImpl(ShortenerService shortenerService, PersistenceService persistenceService) {
        this.shortenerService = shortenerService;
        this.persistenceService = persistenceService;
    }

    @Override
    public String create(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new ShortenerException(ErrorTypeEnum.EMPTY_URL);
        }

        String code = shortenerService.shortenUrl(url);
        ShortenedUrl shortUrl = new ShortenedUrl(code, url);
        persistenceService.save(shortUrl);
        return shortUrl.getCode();
    }

    @Override
    public String get(String code) {
        return persistenceService.getUrl(code)
                .map(ShortenedUrl::getUrl)
                .orElseThrow(() -> new ShortenerException(ErrorTypeEnum.URL_NOT_FOUND));
    }
}
