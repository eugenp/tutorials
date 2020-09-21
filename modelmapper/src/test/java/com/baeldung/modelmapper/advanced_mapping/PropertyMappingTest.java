package com.baeldung.modelmapper.advanced_mapping;

import org.junit.Test;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PropertyMappingTest {
    @Test
    public void givenPropertyMapping_whenMappingSourceObject_thenDestinationObjectShouldContainAllProperties() {
        ModelMapper modelMapper = new ModelMapper();
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

    @Test
    public void givenPropertyMappingWithSkippedProperty_whenMappingSourceObject_thenDestinationObjectShouldContainAllProperties() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Corporate, CorporateDto> typeMap = modelMapper.createTypeMap(Corporate.class, CorporateDto.class);
        typeMap.addMapping(corp -> corp.getHeadquarterAddress().getStreetNo(), CorporateDto::setNoOfStreet);
        typeMap.addMapping(Corporate::getFoundationYear, CorporateDto::setFoundedIn);
        typeMap.addMappings(mapper -> mapper.skip(CorporateDto::setHeadquarterCity));

        Corporate given = new Corporate(
          new Address("Rheinlanddamm", "Dortmund", 109),
          new Manager(
            new Name("Robert", "Gyoza")
          ),
          1909
        );
        CorporateDto actual = typeMap.map(given);

        assertThat(actual.getHeadquarterCity()).isNull();
        assertThat(actual.getHeadquarterStreet()).isEqualTo("Rheinlanddamm");
        assertThat(actual.getManagerFirstName()).isEqualTo("Robert");
        assertThat(actual.getManagerLastName()).isEqualTo("Gyoza");
        assertThat(actual.getFoundedIn()).isEqualTo("1909");
        assertThat(actual.getNoOfStreet()).isEqualTo("109");
    }

    @Test
    public void givenConverter_whenMappingSourceObject_thenDestinationObjectShouldContainAllProperties() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Corporate, CorporateDto> typeMap = modelMapper.createTypeMap(Corporate.class, CorporateDto.class);
        typeMap.addMapping(corp -> corp.getHeadquarterAddress().getStreetNo(), CorporateDto::setNoOfStreet);
        typeMap.addMapping(Corporate::getFoundationYear, CorporateDto::setFoundedIn);

        Converter<String, String> toUppercase = ctx -> ctx.getSource() == null ? null : ctx.getSource().toUpperCase();
        typeMap.addMappings(mapper -> mapper.using(toUppercase).map(corp -> corp.getManager().getName().getLastName(), CorporateDto::setManagerLastName));

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
        assertThat(actual.getManagerLastName()).isEqualTo("GYOZA");
        assertThat(actual.getFoundedIn()).isEqualTo("1909");
        assertThat(actual.getNoOfStreet()).isEqualTo("109");
    }

    @Test
    public void givenCondition_whenMappingSourceObject_thenDestinationObjectShouldContainAllProperties() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Corporate, CorporateDto> typeMap = modelMapper.createTypeMap(Corporate.class, CorporateDto.class);
        typeMap.addMapping(corp -> corp.getHeadquarterAddress().getStreetNo(), CorporateDto::setNoOfStreet);
        typeMap.addMapping(Corporate::getFoundationYear, CorporateDto::setFoundedIn);

        Condition<Integer, String> greaterThan1900 = ctx -> ctx.getSource() > 1910;
        typeMap.addMappings(mapper -> mapper.when(greaterThan1900).map(Corporate::getFoundationYear, CorporateDto::setFoundedIn));

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
        assertThat(actual.getFoundedIn()).isNull();
        assertThat(actual.getNoOfStreet()).isEqualTo("109");
    }
}