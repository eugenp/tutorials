package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.domain.model.CovidCase;
import com.baeldung.hexagonal.architecture.domain.service.CovidCaseServicePortImplementation;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CovidCaseServicePortTest extends TestCase {

    @TestConfiguration
    static class CovidCaseServicePortTestConfig {
        @Bean
        public CovidCaseServicePort covidCaseServicePort() {
            return new CovidCaseServicePortImplementation();
        }
    }

    @MockBean
    private CovidCaseRepository covidCaseRepository;

    @Autowired
    private CovidCaseServicePort covidCaseServicePort;

    @Before
    public void setUp() {
        CovidCase james = new CovidCase(1, "james", 23, "delhi");
        CovidCase john = new CovidCase(2, "john", 27, "bangalore");
        CovidCase david = new CovidCase(3, "david", 33, "goa");


        List<CovidCase> products = Arrays.asList(james, john, david);

        Mockito.when(covidCaseRepository.getCovidCases()).thenReturn(products);
        Mockito.when(covidCaseRepository.getCovidCaseById(james.getId())).thenReturn(james);
        Mockito.when(covidCaseRepository.getCovidCaseById(5)).thenReturn(null);
    }

    @Test
    public void whenValidProductId_thenProductShouldBeFound() {
        Integer id = 1;
        CovidCase covidCase = covidCaseServicePort.getCovidCaseById(1);

        assertThat(covidCase.getId()).isEqualTo(id);

    }

    @Test
    public void whenInValidProductId_thenProductShouldNotBeFound() {
        CovidCase covidCase = covidCaseServicePort.getCovidCaseById(5);

        assertThat(covidCase).isNull();
        verifyGetByCovidCaseIdIsCalledOnce();
    }

    @Test
    public void givenThreeProducts_whenGetAllProducts_thenThreeProductsReturned() {
        CovidCase james = new CovidCase(1, "james", 23, "delhi");
        CovidCase john = new CovidCase(2, "john", 27, "bangalore");
        CovidCase david = new CovidCase(3, "david", 33, "goa");

        List<CovidCase> products = Arrays.asList(james, john, david);

        assertThat(products).hasSize(3).extracting(CovidCase::getPatientName).contains(james.getPatientName(), john.getPatientName(), david.getPatientName());

    }


    private void verifyGetByCovidCaseIdIsCalledOnce() {
        Mockito.verify(covidCaseRepository, VerificationModeFactory.times(1)).getCovidCaseById(Mockito.anyInt());
        Mockito.reset(covidCaseRepository);
    }

}