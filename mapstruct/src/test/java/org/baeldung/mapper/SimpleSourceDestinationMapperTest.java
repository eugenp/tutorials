package org.baeldung.mapper;

import static org.junit.Assert.*;

import org.baeldung.dto.SimpleSource;
import org.baeldung.entity.SimpleDestination;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class SimpleSourceDestinationMapperTest {

	@Test
	public void givenSimpleSourceToSimpleDestination_whenMaps_thenCorrect() {
		SimpleSourceDestinationMapper simpleSourceDestinationMapper = Mappers
				.getMapper(SimpleSourceDestinationMapper.class);
		
		SimpleSource simpleSource = new SimpleSource();
		simpleSource.setName("SourceName");
		simpleSource.setDescription("SourceDescription");
		
		SimpleDestination destination = 
				simpleSourceDestinationMapper.sourceToDestination(simpleSource);
		
		assertEquals(simpleSource.getName(), destination.getName());
		assertEquals(simpleSource.getDescription(), destination.getDescription());
	}
	
	@Test
	public void givenSimpleDestinationToSourceDestination_whenMaps_thenCorrect() {
		SimpleSourceDestinationMapper simpleSourceDestinationMapper = Mappers
				.getMapper(SimpleSourceDestinationMapper.class);
		
		SimpleDestination destination = new SimpleDestination();
		destination.setName("DestinationName");
		destination.setDescription("DestinationDescription");
		
		SimpleSource source = 
				simpleSourceDestinationMapper.destinationToSource(destination);
		
		assertEquals(destination.getName(), source.getName());
		assertEquals(destination.getDescription(), source.getDescription());
	}

}
