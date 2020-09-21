package com.baeldung.modelmapper.advanced_mapping;

import org.junit.Test;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigurationTest {
    @Test
    public void givenPropertyMapping_whenMappingSourceObject_thenDestinationObjectShouldContainAllProperties() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        TypeMap<Corporate, CorporateDto> typeMap = modelMapper.createTypeMap(Corporate.class, CorporateDto.class);
        typeMap.addMapping(corp -> corp.getHeadquarterAddress().getStreetNo(), CorporateDto::setNoOfStreet);
        typeMap.addMapping(Corporate::getFoundationYear, CorporateDto::setFoundedIn);

        Corporate given = new Corporate(
          new Address("Rheinlanddamm", "Dortmund", 109),
          new Manager(
            new Name("Robert", "Gyoza")
          ),
          1909
        );
        CorporateDto actual = typeMap.map(given);

        assertThat(actual.getHeadquarterCity()).isEqualTo("Dortmund");
        assertThat(actual.getHeadquarterStreet()).isEqualTo("Rheinlanddamm");
        assertThat(actual.getManagerFirstName()).isEqualTo("Robert");
        assertThat(actual.getManagerLastName()).isEqualTo("Gyoza");
        assertThat(actual.getFoundedIn()).isEqualTo("1909");
        assertThat(actual.getNoOfStreet()).isEqualTo("109");
    }
}
