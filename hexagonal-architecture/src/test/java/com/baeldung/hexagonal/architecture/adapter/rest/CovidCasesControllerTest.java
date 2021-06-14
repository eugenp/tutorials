package com.baeldung.hexagonal.architecture.adapter.rest;

import com.baeldung.hexagonal.architecture.domain.model.CovidCase;
import com.baeldung.hexagonal.architecture.port.CovidCaseServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CovidCasesController.class)
public class CovidCasesControllerTest extends TestCase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CovidCaseServicePort covidCaseServicePort;

    @Test
    public void givenCovidCases_whenGetCovidCases_thenReturnJsonArray() throws Exception {
        CovidCase james = new CovidCase(1, "james", 23, "delhi");
        CovidCase john = new CovidCase(2, "john", 27, "bangalore");
        CovidCase david = new CovidCase(3, "david", 33, "goa");

        List<CovidCase> products = Arrays.asList(james, john, david);

        given(covidCaseServicePort.getCovidCases()).willReturn(products);

        mvc.perform(get("/api/v1/covidcases")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].patientName", is(james.getPatientName())))
                .andExpect(jsonPath("$[1].city", is(john.getCity())));

        verify(covidCaseServicePort, VerificationModeFactory.times(1)).getCovidCases();
        reset(covidCaseServicePort);
    }

    @Test
    public void givenCovidCase_whenGetCovidCaseById_thenReturnValidCovidCase() throws Exception {
        CovidCase brian = new CovidCase(1, "brian", 23, "delhi");
        given(covidCaseServicePort.getCovidCaseById(Mockito.anyInt())).willReturn(brian);

        mvc.perform(get("/api/v1/covidcases/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(brian.getId())));

        verify(covidCaseServicePort, VerificationModeFactory.times(1)).getCovidCaseById(Mockito.anyInt());
        reset(covidCaseServicePort);
    }

}