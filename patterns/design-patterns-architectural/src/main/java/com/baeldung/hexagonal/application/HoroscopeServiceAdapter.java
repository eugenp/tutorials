package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.baeldung.hexagonal.domain.HoroscopeServicePort;

public class HoroscopeServiceAdapter implements HoroscopeServicePort {
    
private HoroscopeRepositoryPort horoscopeRepositoryPort;
    
    public HoroscopeServiceAdapter(HoroscopeRepositoryPort horoscopeRepositoryPort) {
        this.horoscopeRepositoryPort = horoscopeRepositoryPort;
    }
    
    @Override
    public String execute(String zodiacSign) {
        String horoscope = horoscopeRepositoryPort.getHoroscope(zodiacSign);
        System.out.println(horoscope);
        return horoscope;
    }
}
