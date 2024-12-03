package com.baeldung.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DozerIntegrationTest {
    private final long GMT_DIFFERENCE = 46800000;

    DozerBeanMapper mapper;

    @Before
    public void before() throws Exception {
        mapper = new DozerBeanMapper();
    }

    BeanMappingBuilder builder = new BeanMappingBuilder() {

        @Override
        protected void configure() {
            mapping(Person.class, Personne.class).fields("name", "nom").fields("nickname", "surnom");

        }
    };
    BeanMappingBuilder builderMinusAge = new BeanMappingBuilder() {

        @Override
        protected void configure() {
            mapping(Person.class, Personne.class).fields("name", "nom").fields("nickname", "surnom").exclude("age");

        }
    };

    @Test
    public void givenApiMapper_whenMaps_thenCorrect() {
        mapper.addMapping(builder);

        Personne frenchAppPerson = new Personne("Sylvester Stallone", "Rambo", 70);
        Person englishAppPerson = mapper.map(frenchAppPerson, Person.class);

        assertEquals(englishAppPerson.getName(), frenchAppPerson.getNom());
        assertEquals(englishAppPerson.getNickname(), frenchAppPerson.getSurnom());
        assertEquals(englishAppPerson.getAge(), frenchAppPerson.getAge());
    }

    @Test
    public void givenApiMapper_whenMapsOnlySpecifiedFields_thenCorrect() {
        mapper.addMapping(builderMinusAge);

        Person englishAppPerson = new Person("Sylvester Stallone", "Rambo", 70);
        Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);

        assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
        assertEquals(frenchAppPerson.getSurnom(), englishAppPerson.getNickname());
        assertEquals(frenchAppPerson.getAge(), 0);
    }

    @Test
    public void givenApiMapper_whenMapsBidirectionally_thenCorrect() {
        mapper.addMapping(builder);

        Person englishAppPerson = new Person("Sylvester Stallone", "Rambo", 70);
        Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);

        assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
        assertEquals(frenchAppPerson.getSurnom(), englishAppPerson.getNickname());
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
        configureMapper("dozer_mapping.xml");

        Personne frenchAppPerson = new Personne("Sylvester Stallone", "Rambo", 70);
        Person englishAppPerson = mapper.map(frenchAppPerson, Person.class);

        assertEquals(englishAppPerson.getName(), frenchAppPerson.getNom());
        assertEquals(englishAppPerson.getNickname(), frenchAppPerson.getSurnom());
        assertEquals(englishAppPerson.getAge(), frenchAppPerson.getAge());
    }

    @Test
    public void givenSrcAndDestWithDifferentFieldNamesWithCustomMapper_whenMapsBidirectionally_thenCorrect() {
        configureMapper("dozer_mapping.xml");

        Person englishAppPerson = new Person("Dwayne Johnson", "The Rock", 44);
        Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);

        assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
        assertEquals(frenchAppPerson.getSurnom(), englishAppPerson.getNickname());
        assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
    }

    @Ignore("place dozer_mapping.xml at a location of your choice and copy/paste the path after file: in configureMapper method")
    @Test
    public void givenMappingFileOutsideClasspath_whenMaps_thenCorrect() {
        configureMapper("file:e:/dozer_mapping.xml");

        Person englishAppPerson = new Person("Marshall Bruce Mathers III", "Eminem", 43);
        Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);

        assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
        assertEquals(frenchAppPerson.getSurnom(), englishAppPerson.getNickname());
        assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
    }

    @Test
    public void givenSrcAndDest_whenMapsOnlySpecifiedFields_thenCorrect() {
        configureMapper("dozer_mapping2.xml");

        Person englishAppPerson = new Person("Shawn Corey Carter", "Jay Z", 46);
        Personne frenchAppPerson = mapper.map(englishAppPerson, Personne.class);

        assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
        assertEquals(frenchAppPerson.getSurnom(), englishAppPerson.getNickname());
        assertEquals(frenchAppPerson.getAge(), 0);
    }

    @Test
    public void givenAnnotatedSrcFields_whenMapsToRightDestField_thenCorrect() {
        Person2 englishAppPerson = new Person2("Jean-Claude Van Damme", "JCVD", 55);
        Personne2 frenchAppPerson = mapper.map(englishAppPerson, Personne2.class);
        assertEquals(frenchAppPerson.getNom(), englishAppPerson.getName());
        assertEquals(frenchAppPerson.getSurnom(), englishAppPerson.getNickname());
        assertEquals(frenchAppPerson.getAge(), englishAppPerson.getAge());
    }

    @Test
    public void givenAnnotatedSrcFields_whenMapsToRightDestFieldBidirectionally_thenCorrect() {
        Personne2 frenchAppPerson = new Personne2("Jason Statham", "transporter", 49);
        Person2 englishAppPerson = mapper.map(frenchAppPerson, Person2.class);
        assertEquals(englishAppPerson.getName(), frenchAppPerson.getNom());
        assertEquals(englishAppPerson.getNickname(), frenchAppPerson.getSurnom());
        assertEquals(englishAppPerson.getAge(), frenchAppPerson.getAge());
    }

    @Test
    public void givenSrcAndDestWithDifferentFieldTypes_whenAbleToCustomConvert_thenCorrect() {
        configureMapper("dozer_custom_convertor.xml");

        String dateTime = "2007-06-26T21:22:39Z";
        long timestamp = Long.parseLong("1182882159000");

        Person3 person = new Person3("Rich", dateTime);
        Personne3 person0 = mapper.map(person, Personne3.class);

        long timestampToTest = person0.getDtob();
        assertTrue(timestampToTest == timestamp || timestampToTest >= timestamp - GMT_DIFFERENCE || timestampToTest <= timestamp + GMT_DIFFERENCE);
    }

    @Test
    public void givenSrcAndDestWithDifferentFieldTypes_whenAbleToCustomConvertBidirectionally_thenCorrect() {
        long timestamp = Long.parseLong("1182882159000");
        Personne3 person = new Personne3("Rich", timestamp);
        configureMapper("dozer_custom_convertor.xml");

        Person3 person0 = mapper.map(person, Person3.class);
        String timestampTest = person0.getDtob();

        assertTrue(timestampTest.charAt(10) == 'T' && timestampTest.charAt(19) == 'Z');
    }

    public void configureMapper(String... mappingFileUrls) {
        mapper.setMappingFiles(Arrays.asList(mappingFileUrls));
    }

}
