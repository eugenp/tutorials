package com.baeldung.component.autoproxying;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class RandomIntGenerator {
    private final Random random = new Random();
    private final DataCache dataCache;

    public RandomIntGenerator(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    public int generate(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
