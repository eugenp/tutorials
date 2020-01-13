package com.baeldung.hexagonal.domain;

import java.util.function.Function;

public class HoroscopeService implements Function<String, String>{

    private HoroscopeRepositoryPort horoscopeRepositoryPort;
    
    public HoroscopeService(HoroscopeRepositoryPort horoscopeRepositoryPort) {
        this.horoscopeRepositoryPort = horoscopeRepositoryPort;
    }
    
    @Override
    public String apply(String zodiacSign) {
        return horoscopeRepositoryPort.getHoroscope(zodiacSign);
    }

}
