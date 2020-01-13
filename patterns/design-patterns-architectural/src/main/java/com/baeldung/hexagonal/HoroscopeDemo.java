package com.baeldung.hexagonal;

import com.baeldung.hexagonal.application.ConsoleHoroscopeWriterAdapter;
import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.baeldung.hexagonal.domain.HoroscopeWriterPort;
import com.baeldung.hexagonal.infrastructure.InMemoryHoroscopeRepositoryAdapter;

/**
 * Demonstrate hexagonal architecture with a simple horoscope use case.
 */
public class HoroscopeDemo {
    
    public static void main(String[] args) {
        
        HoroscopeRepositoryPort horoscopeRepository = new InMemoryHoroscopeRepositoryAdapter();
        HoroscopeWriterPort horoscopeWriter = new ConsoleHoroscopeWriterAdapter(horoscopeRepository);
        
        final String[] zodiacSigns = {"ARIES", "TAURUS", "GEMINI", "CANCER"};
        for (String zodiacSign : zodiacSigns) {
            horoscopeWriter.writeHoroscope(zodiacSign);
        }
    }
}
