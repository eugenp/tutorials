package com.baeldung.hexagonal;

public interface RandomNumberGeneratorPort {

    Integer generateRandomNumber(Integer lowerLimit, Integer upperLimit);
}
