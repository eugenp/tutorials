package com.baeldung.modelmapper.advanced_mapping;

import com.baeldung.modelmapper.advanced_mapping.Address;
import com.baeldung.modelmapper.advanced_mapping.Corporate;
import com.baeldung.modelmapper.advanced_mapping.CorporateDto;
import com.baeldung.modelmapper.advanced_mapping.Manager;
import com.baeldung.modelmapper.advanced_mapping.Name;
import org.junit.Test;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidateMappingTest {
    @Test
    public void givenIncompleteMapping_whenMappingSourceObject_thenValidationShouldFail() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Corporate, CorporateDto> typeMap = modelMapper.createTypeMap(Corporate.class, CorporateDto.class);
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
        assertThatThrownBy(typeMap::validate);
    }
}
