package com.baeldung.exclude_urls_filter.service;

import org.springframework.stereotype.Service;

@Service
public class FAQServiceImpl implements FAQService {

    private static final String HELPLINE_NUMBER = "+1 888-777-66";

    @Override
    public String getHelpLineNumber() {
        return HELPLINE_NUMBER;
    }

}
