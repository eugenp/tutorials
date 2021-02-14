package com.baeldung.soap.ws.client;

import com.baeldung.soap.ws.client.generated.Country;
import com.baeldung.soap.ws.client.generated.CountryService;
import com.baeldung.soap.ws.client.generated.Currency;
import org.junit.jupiter.api.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CountryServiceClientTest {

    CountryServiceClient countryServiceClient;
    CountryService countryService;

    @BeforeEach
    void setUp() {
        countryService = mock(CountryService.class);
        countryServiceClient = new CountryServiceClient(countryService);
    }

    @DisplayName("Get capital by country name when country not found")
    @Test
    void getCapitalByCountryName_WhenCountryDoesNotExists_ThenShouldThrowCountryNotFoundException() {
        doThrow(CountryNotFoundException.class).when(countryService).findByName(any());
        Assertions.assertThrows(CountryNotFoundException.class, () -> countryServiceClient.getCapitalByCountryName(any()));
    }

    @DisplayName("Get capital by country name when country is India then should return capital")
    @Test
    void getCapitalByCountryName_WhenCountryEqualsIndia_ThenShouldReturnCapital() {
        Country country = mock(Country.class);

        doReturn("New Delhi").when(country).getCapital();
        doReturn(country).when(countryService).findByName("India");

        Assertions.assertEquals("New Delhi", countryServiceClient.getCapitalByCountryName("India"));
    }

    @DisplayName("Get population by country name when country not found")
    @Test
    void getPopulationByCountryName_WhenCountryDoesNotExists_ThenShouldThrowCountryNotFoundException() {
        doThrow(CountryNotFoundException.class).when(countryService).findByName(any());
        Assertions.assertThrows(CountryNotFoundException.class, () -> countryServiceClient.getPopulationByCountryName(any()));
    }

    @DisplayName("Get population by country name when country is India then should return population")
    @Test
    void getPopulationByCountryName_WhenCountryEqualsIndia_ThenShouldReturnPopulation() {
        Country country = mock(Country.class);

        doReturn(1000000).when(country).getPopulation();
        doReturn(country).when(countryService).findByName("India");

        Assertions.assertEquals(1000000, countryServiceClient.getPopulationByCountryName("India"));
    }

    @DisplayName("Get currency by country name when country not found")
    @Test
    void getCurrencyByCountryName_WhenCountryDoesNotExists_ThenShouldThrowCountryNotFoundException() {
        doThrow(CountryNotFoundException.class).when(countryService).findByName(any());
        Assertions.assertThrows(CountryNotFoundException.class, () -> countryServiceClient.getCurrencyByCountryName(any()));
    }

    @DisplayName("Get currency by country name when country is India then should return currency")
    @Test
    void getCurrencyByCountryName_WhenCountryEqualsIndia_ThenShouldReturnCurrency() {
        Country country = mock(Country.class);

        doReturn(Currency.INR).when(country).getCurrency();
        doReturn(country).when(countryService).findByName("India");

        Assertions.assertEquals(Currency.INR, countryServiceClient.getCurrencyByCountryName("India"));
    }

}