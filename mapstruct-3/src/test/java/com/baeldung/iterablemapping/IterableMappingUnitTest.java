package com.baeldung.iterablemapping;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class IterableMappingUnitTest {
	
    @Test
    void givenStringDatewhenDateFormatIsUsed_thenMapToLocalDate() {
        DateMapper mapper = Mappers.getMapper(DateMapper.class);
		
        assertThat(mapper.stringsToLocalDates(List.of("2025-05-10", "2024-12-25")))
          .containsExactly(LocalDate.of(2025, 5, 10), LocalDate.of(2024, 12, 25));
    }
	
    @Test
    void givenStringDatewhenDateFormatIsNotRespected_thenThrowException() {
        DateMapper mapper = Mappers.getMapper(DateMapper.class);
		
        assertThatThrownBy(() -> mapper.stringsToLocalDates(List.of("2025/05/10")))
          .isInstanceOf(DateTimeParseException.class);
    }
	
    @Test
    void givenUserWithPasswordwhenExcludePassword_thenConvertLoginOnly() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        List<UserDto> result = mapper.toDto(List.of(new User("admin", "@admin@2026")));
		
        assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(new UserDto("admin"));
    }
	
    @Test
    void whenListIsNull_thenReturnEmptyCollection() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
		
        assertThat(mapper.toDto(null)).isEmpty();
    }

}
