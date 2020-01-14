package com.baeldung.hexagonal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.baeldung.hexagonal.application.HoroscopeServiceAdapter;
import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.baeldung.hexagonal.domain.HoroscopeServicePort;
import com.baeldung.hexagonal.infrastructure.InMemoryHoroscopeRepositoryAdapter;

public class HoroscopeServiceUnitTest {

    private static HoroscopeRepositoryPort horoscopeRepository = new InMemoryHoroscopeRepositoryAdapter();
    
    @Test
    public void givenHoroscopeRepository_whenCalledApplyWithCorrectZodiac_thenReturnsExpectedHorocope() {
        HoroscopeServicePort horoscopeService = new HoroscopeServiceAdapter(horoscopeRepository);
        assertEquals("You are likely to hit at least one obstacle today", horoscopeService.execute("ARIES"));
    }
 
    @Test
    public void givenHoroscopeRepository_whenCalledApplyWithIncorrectZodiac_thenReturnsErrorText() {
        HoroscopeServicePort horoscopeService = new HoroscopeServiceAdapter(horoscopeRepository);
        assertEquals("The input zodiac sign [XYZ] is not valid", horoscopeService.execute("XYZ"));
    }
}