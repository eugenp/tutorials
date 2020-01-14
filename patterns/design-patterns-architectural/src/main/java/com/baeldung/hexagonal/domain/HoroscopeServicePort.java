package com.baeldung.hexagonal.domain;

public interface HoroscopeServicePort {
    String execute(String zodiacSign);
}
