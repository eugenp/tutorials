package com.baeldung.testing.easymock;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ForecastProcessorTest {
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

    @SuppressWarnings("unchecked")
    @Test
    public void givenNonEmptyListOfLocationNames_whenWeatherServicePopulatesTemperatures_thenLocationWithMaxTempReturned() {
        mockWeatherService.populateTemperature(EasyMock.<List<Location>> anyObject());
        EasyMock.expectLastCall()
            .andAnswer(() -> {
                List<Location> passedLocations = (List<Location>) EasyMock.getCurrentArguments()[0];
                int i = 1;

                for (Location location : passedLocations) {
                    location.setMaximumTemparature("Houston".equals(location.getName()) ? new BigDecimal(MAX_TEMP) : new BigDecimal(MAX_TEMP - i++));
                }
                return null;
            });
        EasyMock.replay(mockWeatherService);
        Location maxTempLocation = forecastProcessor.findLocationWithMaximumTemperature(Arrays.asList("New York", "Chicago", "Houston", "Pasadena"));
        EasyMock.verify(mockWeatherService);
        assertThat(maxTempLocation.getMaximumTemparature(), equalTo(new BigDecimal(MAX_TEMP)));
        assertThat(maxTempLocation.getName(), equalTo("Houston"));
    }
}
