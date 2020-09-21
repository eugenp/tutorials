package com.baeldung.modelmapper.basic_mapping;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.*;

public class BasicMappingTest {
    @Test
    public void whenMappingSourceObject_thenDestinationObjectShouldContainAllProperties() {
        ModelMapper modelMapper = new ModelMapper();
        Corporate given = new Corporate(
          new Address("Rheinlanddamm", "Dortmund"),
          new Manager(
            new Name("Robert", "Gyoza")
          )
        );
        CorporateDto actual = modelMapper.map(given, CorporateDto.class);

        assertThat(actual.getHeadquarterCity()).isEqualTo("Dortmund");
        assertThat(actual.getHeadquarterStreet()).isEqualTo("Rheinlanddamm");
        assertThat(actual.getManagerFirstName()).isEqualTo("Robert");
        assertThat(actual.getManagerLastName()).isEqualTo("Gyoza");
    }
}
