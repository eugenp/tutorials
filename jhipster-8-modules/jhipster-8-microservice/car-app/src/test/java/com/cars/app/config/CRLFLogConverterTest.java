package com.cars.app.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;

class CRLFLogConverterTest {

    @Test
    void transformShouldReturnInputStringWhenMarkerListIsEmpty() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getMarkerList()).thenReturn(null);
        when(event.getLoggerName()).thenReturn("org.hibernate.example.Logger");
        String input = "Test input string";
        CRLFLogConverter converter = new CRLFLogConverter();

        String result = converter.transform(event, input);

        assertEquals(input, result);
    }

    @Test
    void transformShouldReturnInputStringWhenMarkersContainCRLFSafeMarker() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        Marker marker = MarkerFactory.getMarker("CRLF_SAFE");
        List<Marker> markers = Collections.singletonList(marker);
        when(event.getMarkerList()).thenReturn(markers);
        String input = "Test input string";
        CRLFLogConverter converter = new CRLFLogConverter();

        String result = converter.transform(event, input);

        assertEquals(input, result);
    }

    @Test
    void transformShouldReturnInputStringWhenMarkersNotContainCRLFSafeMarker() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        Marker marker = MarkerFactory.getMarker("CRLF_NOT_SAFE");
        List<Marker> markers = Collections.singletonList(marker);
        when(event.getMarkerList()).thenReturn(markers);
        when(event.getLoggerName()).thenReturn("org.hibernate.example.Logger");
        String input = "Test input string";
        CRLFLogConverter converter = new CRLFLogConverter();

        String result = converter.transform(event, input);

        assertEquals(input, result);
    }

    @Test
    void transformShouldReturnInputStringWhenLoggerIsSafe() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getLoggerName()).thenReturn("org.hibernate.example.Logger");
        String input = "Test input string";
        CRLFLogConverter converter = new CRLFLogConverter();

        String result = converter.transform(event, input);

        assertEquals(input, result);
    }

    @Test
    void transformShouldReplaceNewlinesAndCarriageReturnsWithUnderscoreWhenMarkersDoNotContainCRLFSafeMarkerAndLoggerIsNotSafe() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        List<Marker> markers = Collections.emptyList();
        when(event.getMarkerList()).thenReturn(markers);
        when(event.getLoggerName()).thenReturn("com.mycompany.myapp.example.Logger");
        String input = "Test\ninput\rstring";
        CRLFLogConverter converter = new CRLFLogConverter();

        String result = converter.transform(event, input);

        assertEquals("Test_input_string", result);
    }

    @Test
    void transformShouldReplaceNewlinesAndCarriageReturnsWithAnsiStringWhenMarkersDoNotContainCRLFSafeMarkerAndLoggerIsNotSafeAndAnsiElementIsNotNull() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        List<Marker> markers = Collections.emptyList();
        when(event.getMarkerList()).thenReturn(markers);
        when(event.getLoggerName()).thenReturn("com.mycompany.myapp.example.Logger");
        String input = "Test\ninput\rstring";
        CRLFLogConverter converter = new CRLFLogConverter();
        converter.setOptionList(List.of("red"));

        String result = converter.transform(event, input);

        assertEquals("Test_input_string", result);
    }

    @Test
    void isLoggerSafeShouldReturnTrueWhenLoggerNameStartsWithSafeLogger() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getLoggerName()).thenReturn("org.springframework.boot.autoconfigure.example.Logger");
        CRLFLogConverter converter = new CRLFLogConverter();

        boolean result = converter.isLoggerSafe(event);

        assertTrue(result);
    }

    @Test
    void isLoggerSafeShouldReturnFalseWhenLoggerNameDoesNotStartWithSafeLogger() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getLoggerName()).thenReturn("com.mycompany.myapp.example.Logger");
        CRLFLogConverter converter = new CRLFLogConverter();

        boolean result = converter.isLoggerSafe(event);

        assertFalse(result);
    }

    @Test
    void testToAnsiString() {
        CRLFLogConverter cut = new CRLFLogConverter();
        AnsiElement ansiElement = AnsiColor.RED;

        String result = cut.toAnsiString("input", ansiElement);

        assertThat(result).isEqualTo("input");
    }
}
