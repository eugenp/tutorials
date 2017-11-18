package com.baeldung.orika;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaUnitTest {

    MapperFactory mapperFactory;

    CustomMapper<Personne3, Person3> customMapper;

    // constant to help us cover time zone differences
    private final long GMT_DIFFERENCE = 46800000;

    //

    @Before
    public void before() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        customMapper = new PersonCustomMapper();
    }

    @Test
    public void givenSrcAndDest_whenMaps_thenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source("Baeldung", 10);
        Dest dest = mapper.map(src, Dest.class);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsReverse_thenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).byDefault();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Dest src = new Dest("Baeldung", 10);
        Source dest = mapper.map(src, Source.class);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsByObject_thenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).byDefault();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source("Baeldung", 10);
        Dest dest = new Dest();
        mapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsUsingBoundMapper_thenCorrect() {
        BoundMapperFacade<Source, Dest> boundMapper = mapperFactory.getMapperFacade(Source.class, Dest.class);
        Source src = new Source("baeldung", 10);
        Dest dest = boundMapper.map(src);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsUsingBoundMapperInReverse_thenCorrect() {
        BoundMapperFacade<Source, Dest> boundMapper = mapperFactory.getMapperFacade(Source.class, Dest.class);
        Dest src = new Dest("baeldung", 10);
        Source dest = boundMapper.mapReverse(src);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsUsingBoundMapperByObject_thenCorrect() {
        BoundMapperFacade<Source, Dest> boundMapper = mapperFactory.getMapperFacade(Source.class, Dest.class);
        Source src = new Source("baeldung", 10);
        Dest dest = new Dest();
        boundMapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDest_whenMapsUsingBoundMapperByObjectInReverse_thenCorrect() {
        BoundMapperFacade<Source, Dest> boundMapper = mapperFactory.getMapperFacade(Source.class, Dest.class);
        Dest src = new Dest("baeldung", 10);
        Source dest = new Source();
        boundMapper.mapReverse(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcAndDestWithDifferentFieldNames_whenMaps_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class).field("nom", "name").field("surnom", "nickname").field("age", "age").register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        Personne frenchPerson = new Personne("Claire", "cla", 25);
        Person englishPerson = mapper.map(frenchPerson, Person.class);

        assertEquals(englishPerson.getName(), frenchPerson.getNom());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());

    }

    @Test
    public void givenBothDifferentAndSameFieldNames_whenFailsToMapSameNameFieldAutomatically_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class).field("nom", "name").field("surnom", "nickname").register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Personne frenchPerson = new Personne("Claire", "cla", 25);

        Person englishPerson = mapper.map(frenchPerson, Person.class);
        assertFalse(englishPerson.getAge() == frenchPerson.getAge());

    }

    @Test
    public void givenBothDifferentAndSameFieldNames_whenMapsSameNameFieldByDefault_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class).field("nom", "name").field("surnom", "nickname").byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        Personne frenchPerson = new Personne("Claire", "cla", 25);

        Person englishPerson = mapper.map(frenchPerson, Person.class);
        assertEquals(englishPerson.getName(), frenchPerson.getNom());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());

    }

    @Test
    public void givenUnidirectionalMappingSetup_whenMapsUnidirectionally_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class).fieldAToB("nom", "name").fieldAToB("surnom", "nickname").fieldAToB("age", "age").register();
        ;
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Personne frenchPerson = new Personne("Claire", "cla", 25);

        Person englishPerson = mapper.map(frenchPerson, Person.class);
        assertEquals(englishPerson.getName(), frenchPerson.getNom());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());

    }

    @Test
    public void givenSrcAndDest_whenCanExcludeField_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class).exclude("nom").field("surnom", "nickname").field("age", "age").register();
        MapperFacade mapper = mapperFactory.getMapperFacade();

        Personne frenchPerson = new Personne("Claire", "cla", 25);
        Person englishPerson = mapper.map(frenchPerson, Person.class);

        assertEquals(null, englishPerson.getName());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());
    }

    @Test
    public void givenSpecificConstructorToUse_whenMaps_thenCorrect() {
        mapperFactory.classMap(Personne.class, Person.class).constructorB().field("nom", "name").field("surnom", "nickname").field("age", "age").register();
        ;
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Personne frenchPerson = new Personne("Claire", "cla", 25);

        Person englishPerson = mapper.map(frenchPerson, Person.class);
        assertEquals(englishPerson.getName(), frenchPerson.getNom());
        assertEquals(englishPerson.getNickname(), frenchPerson.getSurnom());
        assertEquals(englishPerson.getAge(), frenchPerson.getAge());

    }

    @Test
    public void givenSrcWithListAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() {
        mapperFactory.classMap(PersonNameList.class, PersonNameParts.class).field("nameList[0]", "firstName").field("nameList[1]", "lastName").register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        List<String> nameList = Arrays.asList(new String[] { "Sylvester", "Stallone" });
        PersonNameList src = new PersonNameList(nameList);
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        assertEquals(dest.getFirstName(), "Sylvester");
        assertEquals(dest.getLastName(), "Stallone");
    }

    @Test
    public void givenSrcWithArrayAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() {
        mapperFactory.classMap(PersonNameArray.class, PersonNameParts.class).field("nameArray[0]", "firstName").field("nameArray[1]", "lastName").register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        String[] nameArray = new String[] { "Vin", "Diesel" };
        PersonNameArray src = new PersonNameArray(nameArray);
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        assertEquals(dest.getFirstName(), "Vin");
        assertEquals(dest.getLastName(), "Diesel");
    }

    @Test
    public void givenSrcWithMapAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() {
        mapperFactory.classMap(PersonNameMap.class, PersonNameParts.class).field("nameMap['first']", "firstName").field("nameMap[\"last\"]", "lastName").register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("first", "Leornado");
        nameMap.put("last", "DiCaprio");
        PersonNameMap src = new PersonNameMap(nameMap);
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        assertEquals(dest.getFirstName(), "Leornado");
        assertEquals(dest.getLastName(), "DiCaprio");
    }

    @Test
    public void givenSrcWithNestedFields_whenMaps_thenCorrect() {
        mapperFactory.classMap(PersonContainer.class, PersonNameParts.class).field("name.firstName", "firstName").field("name.lastName", "lastName").register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        PersonContainer src = new PersonContainer(new Name("Nick", "Canon"));
        PersonNameParts dest = mapper.map(src, PersonNameParts.class);
        assertEquals(dest.getFirstName(), "Nick");
        assertEquals(dest.getLastName(), "Canon");
    }

    @Test
    public void givenSrcWithNullField_whenMapsThenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).byDefault();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source(null, 10);
        Dest dest = mapper.map(src, Dest.class);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenSrcWithNullAndGlobalConfigForNoNull_whenFailsToMap_ThenCorrect() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
        mapperFactory.classMap(Source.class, Dest.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source(null, 10);
        Dest dest = new Dest("Clinton", 55);
        mapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), "Clinton");
    }

    @Test
    public void givenSrcWithNullAndLocalConfigForNoNull_whenFailsToMap_ThenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).field("age", "age").mapNulls(false).field("name", "name").byDefault().register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source(null, 10);
        Dest dest = new Dest("Clinton", 55);
        mapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), "Clinton");
    }

    @Test
    public void givenDestWithNullReverseMappedToSource_whenMapsByDefault_thenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).byDefault();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Dest src = new Dest(null, 10);
        Source dest = new Source("Vin", 44);
        mapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), src.getName());
    }

    @Test
    public void givenDestWithNullReverseMappedToSourceAndLocalConfigForNoNull_whenFailsToMap_thenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).field("age", "age").mapNullsInReverse(false).field("name", "name").byDefault().register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Dest src = new Dest(null, 10);
        Source dest = new Source("Vin", 44);
        mapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), "Vin");
    }

    @Test
    public void givenSrcWithNullAndFieldLevelConfigForNoNull_whenFailsToMap_ThenCorrect() {
        mapperFactory.classMap(Source.class, Dest.class).field("age", "age").fieldMap("name", "name").mapNulls(false).add().byDefault().register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Source src = new Source(null, 10);
        Dest dest = new Dest("Clinton", 55);
        mapper.map(src, dest);
        assertEquals(dest.getAge(), src.getAge());
        assertEquals(dest.getName(), "Clinton");
    }

    @Test
    public void givenSrcAndDest_whenCustomMapperWorks_thenCorrect() {
        mapperFactory.classMap(Personne3.class, Person3.class).customize(customMapper).register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        long timestamp = new Long("1182882159000");
        Personne3 personne3 = new Personne3("Leornardo", timestamp);
        Person3 person3 = mapper.map(personne3, Person3.class);

        String timestampTest = person3.getDtob();
        // since different timezones will resolve the timestamp to a different
        // datetime string, it suffices to check only for format rather than
        // specific date
        assertTrue(timestampTest.charAt(10) == 'T' && timestampTest.charAt(19) == 'Z');
    }

    @Test
    public void givenSrcAndDest_whenCustomMapperWorksBidirectionally_thenCorrect() {
        mapperFactory.classMap(Personne3.class, Person3.class).customize(customMapper).register();
        MapperFacade mapper = mapperFactory.getMapperFacade();

        String dateTime = "2007-06-26T21:22:39Z";
        long timestamp = new Long("1182882159000");
        Person3 person3 = new Person3("Leornardo", dateTime);
        Personne3 personne3 = mapper.map(person3, Personne3.class);
        long timestampToTest = personne3.getDtob();
        /*
        	 * since different timezones will resolve the datetime to a different
        	 * unix timestamp, we must provide a range of tolerance
        	 */
        assertTrue(timestampToTest == timestamp || timestampToTest >= timestamp - GMT_DIFFERENCE || timestampToTest <= timestamp + GMT_DIFFERENCE);

    }

}
