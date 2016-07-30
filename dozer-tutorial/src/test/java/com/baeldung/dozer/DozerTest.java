package com.baeldung.dozer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.Before;
import org.junit.Test;

public class DozerTest {
	DozerBeanMapper mapper = new DozerBeanMapper();

	@Before
	public void before() throws Exception {
		mapper = new DozerBeanMapper();
	}

	BeanMappingBuilder builder = new BeanMappingBuilder() {

		@Override
		protected void configure() {
			mapping(Person.class, Personne.class).fields("name", "nom").fields(
					"nickname", "surnom");

		}
	};
	BeanMappingBuilder builderMinusAge = new BeanMappingBuilder() {

		@Override
		protected void configure() {
			mapping(Person.class, Personne.class).fields("name", "nom")
					.fields("nickname", "surnom").exclude("age");

		}
	};

	@Test
	public void givenApiMapper_whenMaps_thenCorrect() {
		Personne frenchAppPerson = new Personne("Sylvester Stallone", "Rambo",
				70);
		mapper.addMapping(builder);
		Person englishAppPerson = mapper.map(frenchAppPerson, Person.class);
		assertEquals(englishAppPerson.getName(), frenchAppPerson.getNom());
		assertEquals(englishAppPerson.getNickname(),
				frenchAppPerson.getSurnom());
		assertEquals(englishAppPerson.getAge(), frenchAppPerson.getAge());
	}

	@Test
	public void givenApiMapper_whenMapsOnlySpecifiedFields_thenCorrect() {
		Person englishAppPerson = new Person("Sylvester Stallone", "Rambo", 70);
		mapper.addMapping(builderMinusAge);
		Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);
		assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
		assertEquals(frenchAppPerson.getSurnom(),
				englishAppPerson.getNickname());
		assertEquals(frenchAppPerson.getAge(), 0);
	}

	@Test
	public void givenApiMapper_whenMapsBidirectionally_thenCorrect() {
		Person englishAppPerson = new Person("Sylvester Stallone", "Rambo", 70);
		mapper.addMapping(builder);
		Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);
		assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
		assertEquals(frenchAppPerson.getSurnom(),
				englishAppPerson.getNickname());
		assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
	}

	@Test
	public void givenSourceObjectAndDestClass_whenMapsSameNameFieldsCorrectly_thenCorrect() {
		Source source = new Source("Baeldung", 10);
		Dest dest = mapper.map(source, Dest.class);
		assertEquals(dest.getName(), "Baeldung");
		assertEquals(dest.getAge(), 10);
	}

	@Test
	public void givenSourceObjectAndDestObject_whenMapsSameNameFieldsCorrectly_thenCorrect() {
		Source source = new Source("Baeldung", 10);
		Dest dest = new Dest();
		mapper.map(source, dest);
		assertEquals(dest.getName(), "Baeldung");
		assertEquals(dest.getAge(), 10);
	}

	@Test
	public void givenSourceAndDestWithDifferentFieldTypes_whenMapsAndAutoConverts_thenCorrect() {
		Source2 source = new Source2("320", 15.2);
		Dest2 dest = mapper.map(source, Dest2.class);
		assertEquals(dest.getId(), 320);
		assertEquals(dest.getPoints(), 15);
	}

	@Test
	public void givenSrcAndDestWithDifferentFieldNamesWithCustomMapper_whenMaps_thenCorrect() {
		List<String> mappingFiles = new ArrayList<>();
		mappingFiles.add("dozer_mapping.xml");
		Personne frenchAppPerson = new Personne("Sylvester Stallone", "Rambo",
				70);
		mapper.setMappingFiles(mappingFiles);
		Person englishAppPerson = mapper.map(frenchAppPerson, Person.class);
		assertEquals(englishAppPerson.getName(), frenchAppPerson.getNom());
		assertEquals(englishAppPerson.getNickname(),
				frenchAppPerson.getSurnom());
		assertEquals(englishAppPerson.getAge(), frenchAppPerson.getAge());
	}

	@Test
	public void givenSrcAndDestWithDifferentFieldNamesWithCustomMapper_whenMapsBidirectionally_thenCorrect() {
		List<String> mappingFiles = new ArrayList<>();
		mappingFiles.add("dozer_mapping.xml");
		Person englishAppPerson = new Person("Dwayne Johnson", "The Rock", 44);
		mapper.setMappingFiles(mappingFiles);
		Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);
		assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
		assertEquals(frenchAppPerson.getSurnom(),
				englishAppPerson.getNickname());
		assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
	}

//	@Test
//	public void givenMappingFileOutsideClasspath_whenMaps_thenCorrect() {
//		List<String> mappingFiles = new ArrayList<>();
//		mappingFiles.add("file:E:\\dozer_mapping.xml");
//		Person englishAppPerson = new Person("Marshall Bruce Mathers III",
//				"Eminem", 43);
//		mapper.setMappingFiles(mappingFiles);
//		Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);
//		assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
//		assertEquals(frenchAppPerson.getSurnom(),
//				englishAppPerson.getNickname());
//		assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
//	}

	@Test
	public void givenSrcAndDest_whenMapsOnlySpecifiedFields_thenCorrect() {
		List<String> mappingFiles = new ArrayList<>();
		mappingFiles.add("dozer_mapping2.xml");
		Person englishAppPerson = new Person("Shawn Corey Carter", "Jay Z", 46);
		mapper.setMappingFiles(mappingFiles);
		Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);
		assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
		assertEquals(frenchAppPerson.getSurnom(),
				englishAppPerson.getNickname());
		assertEquals(frenchAppPerson.getAge(), 0);
	}

	@Test
	public void givenAnnotatedSrcFields_whenMapsToRightDestField_thenCorrect() {
		Person2 englishAppPerson = new Person2("Jean-Claude Van Damme", "JCVD",
				55);
		Personne2 frenchAppPerson = mapper.map(englishAppPerson,
				Personne2.class);
		assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
		assertEquals(frenchAppPerson.getSurnom(),
				englishAppPerson.getNickname());
		assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
	}

	@Test
	public void givenAnnotatedSrcFields_whenMapsToRightDestFieldBidirectionally_thenCorrect() {
		Personne2 frenchAppPerson = new Personne2("Jason Statham",
				"transporter", 49);
		Person2 englishAppPerson = mapper.map(frenchAppPerson, Person2.class);
		assertEquals(englishAppPerson.getName(), frenchAppPerson.getNom());
		assertEquals(englishAppPerson.getNickname(),
				frenchAppPerson.getSurnom());
		assertEquals(englishAppPerson.getAge(), frenchAppPerson.getAge());
	}

	@Test
	public void givenSrcAndDestWithDifferentFieldTypes_whenAbleToCustomConvert_thenCorrect() {
		String dateTime = "2007-06-26T21:22:39Z";
		long timestamp = new Long("1182882159000");
		Person3 person = new Person3("Rich", dateTime);
		mapper.setMappingFiles(Arrays
				.asList(new String[] { "dozer_custom_convertor.xml" }));
		Personne3 person0 = mapper.map(person, Personne3.class);
		assertEquals(timestamp, person0.getDtob());
	}

	@Test
	public void givenSrcAndDestWithDifferentFieldTypes_whenAbleToCustomConvertBidirectionally_thenCorrect() {
		String dateTime = "2007-06-26T21:22:39Z";
		long timestamp = new Long("1182882159000");
		Personne3 person = new Personne3("Rich", timestamp);
		mapper.setMappingFiles(Arrays
				.asList(new String[] { "dozer_custom_convertor.xml" }));
		Person3 person0 = mapper.map(person, Person3.class);
		assertEquals(dateTime, person0.getDtob());
	}

}
