package com.baeldung.accesing_session_attributes.business.impl;

import java.net.URI;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.baeldung.accesing_session_attributes.business.AgeAnalysisService;
import com.baeldung.accesing_session_attributes.business.entities.NameAgeEntity;

@Component
public class AgeAnalysisServiceImpl implements AgeAnalysisService {
    @Value("${name-analysis-controller.name-age-api-url:https://api.agify.io/?name={0}}")
    private String nameAgeApiUrl;

    @Override
    public ResponseEntity<NameAgeEntity> getAgeAnalysisForName(String nameToAnalyze) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(URI.create(MessageFormat.format(nameAgeApiUrl, nameToAnalyze)), NameAgeEntity.class);
    }

}
