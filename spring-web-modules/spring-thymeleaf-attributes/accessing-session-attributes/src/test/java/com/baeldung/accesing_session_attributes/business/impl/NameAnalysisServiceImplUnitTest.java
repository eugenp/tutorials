package com.baeldung.accesing_session_attributes.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.accesing_session_attributes.business.AgeAnalysisService;
import com.baeldung.accesing_session_attributes.business.CountryAnalysisService;
import com.baeldung.accesing_session_attributes.business.GenderAnalysisService;
import com.baeldung.accesing_session_attributes.business.NameAnalysisService;
import com.baeldung.accesing_session_attributes.business.beans.NameRequest;
import com.baeldung.accesing_session_attributes.business.entities.NameAgeEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameAnalysisEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameCountriesEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameGenderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({ "test" })
public class NameAnalysisServiceImplUnitTest {

    @Autowired
    private ObjectMapper om;
    @MockBean
    private AgeAnalysisService ageAnalysisService;
    @MockBean
    private CountryAnalysisService countryAnalysisService;
    @MockBean
    private GenderAnalysisService genderAnalysisService;
    @Autowired
    private NameAnalysisService toTest;

    @Test
    void givenRigoberto_whenCallCompletesOk_thenNameAnalysisEntityReceived()
            throws JsonMappingException, JsonProcessingException {
        String nameToAnalyze = "Rigoberto";
        NameAgeEntity rigobertoAgeAnalysis = om.readValue("{\"age\":68,\"count\":683,\"name\":\"Rigoberto\"}",
                NameAgeEntity.class);
        NameCountriesEntity rigobertoCountriesAnalysis = om.readValue(
                "{\"country\":[{\"country_id\":\"MX\",\"probability\":0.132},{\"country_id\":\"PA\",\"probability\":0.107},{\"country_id\":\"CR\",\"probability\":0.09},{\"country_id\":\"HN\",\"probability\":0.082},{\"country_id\":\"GT\",\"probability\":0.075}],\"name\":\"Rigoberto\"}",
                NameCountriesEntity.class);
        NameGenderEntity rigobertoGenderAnalysis = om.readValue(
                "{\"count\":13927,\"gender\":\"male\",\"name\":\"Rigoberto\",\"probability\":1.0}",
                NameGenderEntity.class);
        Mockito.when(ageAnalysisService.getAgeAnalysisForName(nameToAnalyze))
                .thenReturn(ResponseEntity.ok(rigobertoAgeAnalysis));
        Mockito.when(countryAnalysisService.getCountryAnalysisForName(nameToAnalyze))
                .thenReturn(ResponseEntity.ok(rigobertoCountriesAnalysis));
        Mockito.when(genderAnalysisService.getGenderAnalysisForName(nameToAnalyze))
                .thenReturn(ResponseEntity.ok(rigobertoGenderAnalysis));

        NameRequest rigobertoRequest = new NameRequest();
        rigobertoRequest.setName(nameToAnalyze);
        CompletableFuture<NameAnalysisEntity> result = toTest.searchForName(rigobertoRequest);

        try {
            NameAnalysisEntity analysisResult = result.get();
            assertEquals(nameToAnalyze, analysisResult.getName());
            assertEquals(rigobertoAgeAnalysis, analysisResult.getAge());
            assertEquals(rigobertoCountriesAnalysis, analysisResult.getCountries());
            assertEquals(rigobertoGenderAnalysis, analysisResult.getGender());

        } catch (Exception e) {
            fail("Not expected exception");
        }
    }

    @Test
    void whenNameAnalysisRequestFails_thenCompletableFutureException() {
        String nameToAnalyze = "Rigoberto";
        Mockito.when(ageAnalysisService.getAgeAnalysisForName(nameToAnalyze))
                .thenThrow(new RuntimeException("Failed age analysis for name"));
        Mockito.when(countryAnalysisService.getCountryAnalysisForName(nameToAnalyze))
                .thenThrow(new RuntimeException("Failed country analysis for name"));
        Mockito.when(genderAnalysisService.getGenderAnalysisForName(nameToAnalyze))
                .thenThrow(new RuntimeException("Failed gender analysis for name"));

        NameRequest rigobertoRequest = new NameRequest();
        rigobertoRequest.setName(nameToAnalyze);
        CompletableFuture<NameAnalysisEntity> result = toTest.searchForName(rigobertoRequest);

        try {
            result.get();
            fail("Expected Execution Exception");

        } catch (ExecutionException e) {

        } catch (InterruptedException e) {
            fail("Expected Execution Exception");
        }
    }
}
