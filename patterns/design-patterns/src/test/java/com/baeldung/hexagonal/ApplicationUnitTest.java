package com.baeldung.hexagonal;

import com.baeldung.hexagonal.datafeed.HttpDataFeedAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationUnitTest {

    private Application application;

    @Before
    public void setUp() {
        this.application = new Application();
    }

    @Test
    public void givenWeatherAlert_whenSentAsHttpDataFeed_thenProcessed() throws Exception {

        // Set up
        WeatherAlert expectedWeatherAlert = new WeatherAlert();
        expectedWeatherAlert.setWeatherAlertType(WeatherAlert.WeatherAlertType.COLD);
        expectedWeatherAlert.setWeatherAlertDate(new Date());

        ObjectMapper mapper = new ObjectMapper();
        byte [] data = mapper.writeValueAsBytes(expectedWeatherAlert);

        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
        when(mockedRequest.getInputStream()).thenReturn(this.createServletInputStream(data));

        // Test
        HttpDataFeedAdapter httpDataFeedAdapter = this.application.getHttpDataFeedAdapter();
        httpDataFeedAdapter.doWeatherAlert(mockedRequest);

        // Assert
        this.application.getNotifiers()
                .stream()
                .forEach(notifierPort -> assertEquals(expectedWeatherAlert, notifierPort.getLatest()));

        this.application.getRepositories()
                .stream()
                .map(repositoryPort -> repositoryPort.findAll())
                .map(allWeatherAlerts -> allWeatherAlerts.get(0))
                .forEach(storedWeatherAlert -> assertEquals(expectedWeatherAlert, storedWeatherAlert));
    }

    /**
     * Create simple mock ServletInputStream that wraps the supplied data.
     *
     * @param data
     * @return ServletInputStream
     * @throws Exception
     */
    private ServletInputStream createServletInputStream(byte [] data) throws Exception {
        final InputStream bais = new ByteArrayInputStream(data);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}