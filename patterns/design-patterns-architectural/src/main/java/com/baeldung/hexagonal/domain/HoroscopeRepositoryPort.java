package com.baeldung.hexagonal.domain;

public interface HoroscopeRepositoryPort {
    String getHoroscope(String zodiacSign);
}
