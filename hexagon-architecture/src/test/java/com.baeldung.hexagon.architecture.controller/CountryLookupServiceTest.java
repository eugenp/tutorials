package com.baeldung.hexagon.architecture;

import com.baeldung.hexagon.architecture.dao.CountryDaoMock;
import com.baeldung.hexagon.architecture.entity.Country;
import com.baeldung.hexagon.architecture.entity.CountryResponse;
import com.baeldung.hexagon.architecture.service.CountryLookupService;
import com.baeldung.hexagon.architecture.service.FakeMessageQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CountryLookupServiceTest {
    private CountryDaoMock countryDaoMock = mock(CountryDaoMock.class);
    private FakeMessageQueue fakeMessageQueue = mock(FakeMessageQueue.class);
    private CountryLookupService target;

    @Test
    void shouldFindAndReturnCountryInformation() {
        target = new CountryLookupService(countryDaoMock, fakeMessageQueue);
        Country resultingCountry = new Country("Sweden", 11000000);

        when(countryDaoMock.getCountryByName("Sweden")).thenReturn(resultingCountry);

        CountryResponse result = target.getCountryByName("Sweden");
        assertEquals(result.getCountry().getName(), "Sweden");
        assertEquals(result.getCountry().getPopulation(), 11000000);

        verify(fakeMessageQueue).message(resultingCountry);
    }
}
