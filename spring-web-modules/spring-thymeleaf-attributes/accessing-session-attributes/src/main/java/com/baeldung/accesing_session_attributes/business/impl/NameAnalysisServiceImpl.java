package com.baeldung.accesing_session_attributes.business.impl;

import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.baeldung.accesing_session_attributes.business.AgeAnalysisService;
import com.baeldung.accesing_session_attributes.business.CountryAnalysisService;
import com.baeldung.accesing_session_attributes.business.GenderAnalysisService;
import com.baeldung.accesing_session_attributes.business.NameAnalysisService;
import com.baeldung.accesing_session_attributes.business.beans.NameRequest;
import com.baeldung.accesing_session_attributes.business.entities.NameAgeEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameAnalysisEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameCountriesEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameGenderEntity;
import com.baeldung.accesing_session_attributes.business.entities.factories.NameAnalysisEntityFactory;

@Component
public class NameAnalysisServiceImpl implements NameAnalysisService {

    private NameRequest lastNameRequest;
    private AgeAnalysisService ageAnalysisService;
    private CountryAnalysisService countryAnalysisService;
    private GenderAnalysisService genderAnalysisService;
    private NameAnalysisEntityFactory nameAnalysisEntityFactory;

    @Autowired
    public NameAnalysisServiceImpl(AgeAnalysisService ageAnalysisService, CountryAnalysisService countryAnalysisService, GenderAnalysisService genderAnalysisService, NameAnalysisEntityFactory nameAnalysisEntityFactory) {
        super();
        this.ageAnalysisService = ageAnalysisService;
        this.countryAnalysisService = countryAnalysisService;
        this.genderAnalysisService = genderAnalysisService;
        this.nameAnalysisEntityFactory = nameAnalysisEntityFactory;

        lastNameRequest = new NameRequest();
        lastNameRequest.setName("Rigoberto");
    }

    public NameRequest getLastNameRequest() {
        return lastNameRequest;
    }

    public CompletableFuture<NameAnalysisEntity> searchForName(NameRequest nameRequest) {
        this.lastNameRequest.setName(nameRequest.getName());
        return analyzeName();
    }

    @Async
    private CompletableFuture<NameAnalysisEntity> analyzeName() {
        try {
            String nameToAnalyze = URLEncoder.encode(lastNameRequest.getName(), "UTF-8");

            ResponseEntity<NameAgeEntity> ageRequestResponse = ageAnalysisService.getAgeAnalysisForName(nameToAnalyze);
            ResponseEntity<NameCountriesEntity> countriesRequestResponse = countryAnalysisService.getCountryAnalysisForName(nameToAnalyze);
            ResponseEntity<NameGenderEntity> genderRequestResponse = genderAnalysisService.getGenderAnalysisForName(nameToAnalyze);

            NameAnalysisEntity nameAnalysis = nameAnalysisEntityFactory.getInstance(lastNameRequest.getName(), genderRequestResponse.getBody(), ageRequestResponse.getBody(), countriesRequestResponse.getBody());
            return CompletableFuture.completedFuture(nameAnalysis);

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
