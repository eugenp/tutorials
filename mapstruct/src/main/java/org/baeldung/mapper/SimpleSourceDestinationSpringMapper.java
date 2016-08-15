package org.baeldung.mapper;

import org.baeldung.dto.SimpleSource;
import org.baeldung.entity.SimpleDestination;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface SimpleSourceDestinationSpringMapper {

	SimpleDestination sourceToDestination(SimpleSource source);
    SimpleSource destinationToSource(SimpleDestination destination);
	
}
