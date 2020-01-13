package com.baeldung.hexagonal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.baeldung.hexagonal.domain.HoroscopeService;
import com.baeldung.hexagonal.infrastructure.InMemoryHoroscopeRepositoryAdapter;

public class HoroscopeServiceUnitTest {

    private static HoroscopeRepositoryPort horoscopeRepository = new InMemoryHoroscopeRepositoryAdapter();
    
    @Test
    public void givenHoroscopeRepository_whenCalledApplyWithCorrectZodiac_thenReturnsExpectedHorocope() {
        HoroscopeService horoscopeService = new HoroscopeService(horoscopeRepository);
        assertEquals("You are likely to hit at least one obstacle today", horoscopeService.apply("ARIES"));
    }
 
    @Test
    public void givenHoroscopeRepository_whenCalledApplyWithIncorrectZodiac_thenReturnsErrorText() {
        HoroscopeService horoscopeService = new HoroscopeService(horoscopeRepository);
        assertEquals("The input zodiac sign [XYZ] is not valid", horoscopeService.apply("XYZ"));
    }
}