package com.baeldung.map.randomkey;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class RandomKeyUnitTest {

    @Test
    void givenMap_whenUsingRandomClass_thenGetRandomKey() {
        Map<String, String> countries = Map.of("Japan", "Tokyo", "Spain", "Madrid");

        List<String> countryNames = new ArrayList<String>(countries.keySet());
        int randomIndex = new Random().nextInt(countryNames.size());
        String randomCountry = countryNames.get(randomIndex);

        assertThat(countries).containsKey(randomCountry);
    }

    @Test
    void givenMap_whenUsingMathRandomMethod_thenGetRandomKey() {
        Map<String, String> countries = Map.of("Japan", "Tokyo", "Spain", "Madrid");

        List<String> countryNames = new ArrayList<String>(countries.keySet());
        int randomIndex = (int) (Math.random() * countryNames.size());
        String randomCountry = countryNames.get(randomIndex);

        assertThat(countries).containsKey(randomCountry);
    }

    @Test
    void givenMap_whenUsingThreadLocalRandomClass_thenGetRandomKey() {
        Map<String, String> countries = Map.of("Japan", "Tokyo", "Spain", "Madrid");

        List<String> countryNames = new ArrayList<String>(countries.keySet());
        int randomIndex = ThreadLocalRandom.current()
            .nextInt(countryNames.size());
        String randomCountry = countryNames.get(randomIndex);

        assertThat(countries).containsKey(randomCountry);
    }

    @Test
    void givenMap_whenUsingSecureRandomClass_thenGetRandomKey() {
        Map<String, String> countries = Map.of("Japan", "Tokyo", "Spain", "Madrid");

        List<String> countryNames = new ArrayList<String>(countries.keySet());
        int randomIndex = new SecureRandom().nextInt(countryNames.size());
        String randomCountry = countryNames.get(randomIndex);

        assertThat(countries).containsKey(randomCountry);
    }

    @Test
    void givenMap_whenUsingCollectionsShuffleMethod_thenGetRandomKey() {
        Map<String, String> countries = Map.of("Japan", "Tokyo", "Spain", "Madrid");

        List<String> countryNames = new ArrayList<String>(countries.keySet());
        Collections.shuffle(countryNames);
        String randomCountry = countryNames.get(0);

        assertThat(countries).containsKey(randomCountry);
    }

}
