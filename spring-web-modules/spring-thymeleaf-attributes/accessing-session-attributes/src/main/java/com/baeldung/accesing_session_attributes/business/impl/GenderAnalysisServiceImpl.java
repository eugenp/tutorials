package com.baeldung.accesing_session_attributes.business.impl;

import java.net.URI;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.baeldung.accesing_session_attributes.business.GenderAnalysisService;
import com.baeldung.accesing_session_attributes.business.entities.NameGenderEntity;

@Component
public class GenderAnalysisServiceImpl implements GenderAnalysisService {
    @Value("${name-analysis-controller.name-gender-api-url:https://api.genderize.io/?name={0}}")
    private String nameGenderApiUrl;

    @Override
    public ResponseEntity<NameGenderEntity> getGenderAnalysisForName(String nameToAnalyze) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(URI.create(MessageFormat.format(nameGenderApiUrl, nameToAnalyze)), NameGenderEntity.class);
    }

}