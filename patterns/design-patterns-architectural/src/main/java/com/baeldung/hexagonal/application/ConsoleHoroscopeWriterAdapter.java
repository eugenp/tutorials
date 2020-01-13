package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.baeldung.hexagonal.domain.HoroscopeService;
import com.baeldung.hexagonal.domain.HoroscopeWriterPort;

public class ConsoleHoroscopeWriterAdapter implements HoroscopeWriterPort {

    private HoroscopeRepositoryPort horoscopeRepository;

    public ConsoleHoroscopeWriterAdapter(HoroscopeRepositoryPort horoscopeRepository) {
        this.horoscopeRepository = horoscopeRepository;
    }
    
    @Override
    public void writeHoroscope(String zodiacSign) {
        HoroscopeService horoscopeService = new HoroscopeService(horoscopeRepository);
        String horoscope = horoscopeService.apply(zodiacSign);
        System.out.println(horoscope);
    }
}