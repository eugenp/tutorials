package com.baeldung.hexagonal;

import com.baeldung.hexagonal.application.HoroscopeServiceAdapter;
import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.baeldung.hexagonal.domain.HoroscopeServicePort;
import com.baeldung.hexagonal.infrastructure.InMemoryHoroscopeRepositoryAdapter;

/**
 * Demonstrate hexagonal architecture with a simple horoscope use case.
 */
public class HoroscopeDemo {
    
    public static void main(String[] args) {
        
        HoroscopeRepositoryPort horoscopeRepository = new InMemoryHoroscopeRepositoryAdapter();
        HoroscopeServicePort horoscopeService = new HoroscopeServiceAdapter(horoscopeRepository);
        
        final String[] zodiacSigns = {"ARIES", "TAURUS", "GEMINI", "CANCER"};
        for (String zodiacSign : zodiacSigns) {
            horoscopeService.execute(zodiacSign);
        }
    }
}
