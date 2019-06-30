package com.baeldung.testing.easymock;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ForecastProcessorUnitTest {
    private static int MAX_TEMP = 90;
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @TestSubject
    private ForecastProcessor forecastProcessor = new ForecastProcessor();

    @Mock
    private WeatherService mockWeatherService;

    @Before
    public void setUp() {
        forecastProcessor.setWeatherService(mockWeatherService);
    }

    @Test
    public void givenLocationName_whenWeatherServicePopulatesTemperatures_thenMaxTempReturned() throws ServiceUnavailableException {
        mockWeatherService.populateTemperature(EasyMock.anyObject(Location.class));
        EasyMock.expectLastCall()
            .andAnswer(() -> {
                Location passedLocation = (Location) EasyMock.getCurrentArguments()[0];
                passedLocation.setMaximumTemparature(new BigDecimal(MAX_TEMP));
                passedLocation.setMinimumTemperature(new BigDecimal(MAX_TEMP - 10));
                return null;
            });
        EasyMock.replay(mockWeatherService);
        BigDecimal maxTemperature = forecastProcessor.getMaximumTemperature("New York");
        EasyMock.verify(mockWeatherService);
        assertThat(maxTemperature, equalTo(new BigDecimal(MAX_TEMP)));
    }
    
    @Test
    public void whenServiceThrowsException_thenNullReturned() throws ServiceUnavailableException {
        mockWeatherService.populateTemperature(EasyMock.anyObject(Location.class));
        EasyMock.expectLastCall().andThrow(new ServiceUnavailableException());
        EasyMock.replay(mockWeatherService);
        Assert.assertNull(forecastProcessor.getMaximumTemperature("New York"));
    }
}
