package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.ErrorTypeEnum;
import com.baeldung.hexagonal.domain.ShortenerException;
import com.baeldung.hexagonal.domain.persistence.PersistenceService;
import com.baeldung.hexagonal.domain.shortener.ShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShortenedUrlServiceUnitTest {

    @InjectMocks private ShortenedUrlServiceImpl service;

    @Mock private ShortenerService shortenerService;
    @Mock private PersistenceService persistenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenUrlIsEmpty_thenCreateShouldRaiseException() {
        ShortenerException ex = assertThrows(ShortenerException.class, () -> service.create(""));

        assertThat(ex.getErrorTypeEnum()).isEqualTo(ErrorTypeEnum.EMPTY_URL);
    }

    @Test
    void whenUrlIsMalformed_thenCreateShouldRaiseException() {
        ShortenerException ex = assertThrows(ShortenerException.class, () -> service.create("google.com"));

        assertThat(ex.getErrorTypeEnum()).isEqualTo(ErrorTypeEnum.MALFORMED_URL);
    }

    @Test
    void whenUrlIsCorrect_thenShouldCreateShortenedCode() {
        String shortCode = "shortcode";
        doNothing().when(persistenceService).save(any());
        when(shortenerService.shortenUrl(any())).thenReturn(shortCode);

        String shortenedUrl = service.create("http://www.google.com");

        assertThat(shortenedUrl).isEqualTo(shortCode);
        verify(shortenerService, times(1)).shortenUrl(any());
        verify(persistenceService, times(1)).save(any());
    }

}