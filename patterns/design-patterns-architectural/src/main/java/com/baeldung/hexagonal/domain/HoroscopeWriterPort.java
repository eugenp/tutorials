package com.baeldung.hexagonal.domain;

public interface HoroscopeWriterPort {
    void writeHoroscope(String zodiacSign);
}
