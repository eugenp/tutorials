package com.baeldung.iterablemapping;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper
public interface DateMapper {
    
    @IterableMapping(dateFormat = "yyyy-MM-dd") 
    List<LocalDate> stringsToLocalDates(List<String> dates);
	
}
