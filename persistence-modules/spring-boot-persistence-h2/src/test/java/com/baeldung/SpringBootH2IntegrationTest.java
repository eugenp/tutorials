package com.baeldung;

import com.baeldung.h2db.springboot.SpringBootH2Application;
import com.baeldung.h2db.springboot.daos.CountryRepository;
import com.baeldung.h2db.springboot.models.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootH2Application.class)
public class SpringBootH2IntegrationTest {

    private static final Country AN_EXPECTED_COUNTRY = buildCountry();

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void givenInitData_whenApplicationStarts_thenDataIsLoaded() {
        Iterable<Country> users = countryRepository.findAll();

        assertThat(users)
          .hasSize(5)
          .contains(AN_EXPECTED_COUNTRY);
    }

    private static Country buildCountry() {
        Country c = new Country();
        c.setId(5);
        c.setName("Canada");
        return c;
    }

}
