package com.baeldung.jackson.annotation;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.baeldung.jackson.annotation.bidirection.ItemWithIdentity;
import com.baeldung.jackson.annotation.bidirection.ItemWithRef;
import com.baeldung.jackson.annotation.bidirection.UserWithIdentity;
import com.baeldung.jackson.annotation.bidirection.UserWithRef;
import com.baeldung.jackson.annotation.date.EventWithFormat;
import com.baeldung.jackson.annotation.date.EventWithSerializer;
import com.baeldung.jackson.annotation.ignore.MyMixInForIgnoreType;
import com.baeldung.jackson.annotation.dtos.withEnum.DistanceEnumWithValue;
import com.baeldung.jackson.annotation.exception.UserWithRoot;
import com.baeldung.jackson.annotation.exception.UserWithRootNamespace;
import com.baeldung.jackson.annotation.jsonview.Item;
import com.baeldung.jackson.annotation.jsonview.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacksonAnnotationUnitTest {

    @Test
    public void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException {
        final ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("attr1"));
        assertThat(result, containsString("val1"));
    }

    @Test
    public void whenSerializingUsingJsonGetter_thenCorrect() throws JsonProcessingException {
        final BeanWithGetter bean = new BeanWithGetter(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    @Test
    public void whenSerializingUsingJsonPropertyOrder_thenCorrect() throws JsonProcessingException {
        final MyBean bean = new MyBean(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    @Test
    public void whenSerializingUsingJsonRawValue_thenCorrect() throws JsonProcessingException {
        final RawBean bean = new RawBean("My bean", "{\"attr\":false}");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("{\"attr\":false}"));
    }

    @Test
    public void whenSerializingUsingJsonRootName_thenCorrect() throws JsonProcessingException {
        final UserWithRoot user = new UserWithRoot(1, "John");

        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final String result = mapper.writeValueAsString(user);

        assertThat(result, containsString("John"));
        assertThat(result, containsString("user"));
    }

    @Test
    public void whenSerializingUsingJsonValue_thenCorrect() throws IOException {
        final String enumAsString = new ObjectMapper().writeValueAsString(DistanceEnumWithValue.MILE);

        assertThat(enumAsString, is("1609.34"));
    }

    @Test
    public void whenSerializingUsingJsonSerialize_thenCorrect() throws JsonProcessingException, ParseException {
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        final String toParse = "20-12-2014 02:30:00";
        final Date date = df.parse(toParse);
        final EventWithSerializer event = new EventWithSerializer("party", date);

        final String result = new ObjectMapper().writeValueAsString(event);
        assertThat(result, containsString(toParse));
    }

    // ========================= Deserializing annotations ============================

    @Test
    public void whenDeserializingUsingJsonCreator_thenCorrect() throws IOException {
        final String json = "{\"id\":1,\"theName\":\"My bean\"}";

        final BeanWithCreator bean = new ObjectMapper().readerFor(BeanWithCreator.class)
            .readValue(json);
        assertEquals("My bean", bean.name);
    }

    @Test
    public void whenDeserializingUsingJsonInject_thenCorrect() throws IOException {
        final String json = "{\"name\":\"My bean\"}";
        final InjectableValues inject = new InjectableValues.Std().addValue(int.class, 1);

        final BeanWithInject bean = new ObjectMapper().reader(inject)
            .forType(BeanWithInject.class)
            .readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals(1, bean.id);
    }

    @Test
    public void whenDeserializingUsingJsonAnySetter_thenCorrect() throws IOException {
        final String json = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

        final ExtendableBean bean = new ObjectMapper().readerFor(ExtendableBean.class)
            .readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals("val2", bean.getProperties()
            .get("attr2"));
    }

    @Test
    public void whenDeserializingUsingJsonSetter_thenCorrect() throws IOException {
        final String json = "{\"id\":1,\"name\":\"My bean\"}";

        final BeanWithGetter bean = new ObjectMapper().readerFor(BeanWithGetter.class)
            .readValue(json);
        assertEquals("My bean", bean.getTheName());
    }

    @Test
    public void whenDeserializingUsingJsonDeserialize_thenCorrect() throws IOException {
        final String json = "{\"name\":\"party\",\"eventDate\":\"20-12-2014 02:30:00\"}";

        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        final EventWithSerializer event = new ObjectMapper().readerFor(EventWithSerializer.class)
            .readValue(json);
        assertEquals("20-12-2014 02:30:00", df.format(event.eventDate));
    }

    // ========================= Inclusion annotations ============================

    @Test
    public void whenSerializingUsingJsonIgnoreProperties_thenCorrect() throws JsonProcessingException {
        final BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));
    }

    @Test
    public void whenSerializingUsingJsonIgnore_thenCorrect() throws JsonProcessingException {
        final BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));
    }

    @Test
    public void whenSerializingUsingJsonIgnoreType_thenCorrect() throws JsonProcessingException, ParseException {
        final UserWithIgnoreType.Name name = new UserWithIgnoreType.Name("John", "Doe");
        final UserWithIgnoreType user = new UserWithIgnoreType(1, name);

        final String result = new ObjectMapper().writeValueAsString(user);

        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("name")));
        assertThat(result, not(containsString("John")));
    }

    @Test
    public void whenSerializingUsingJsonInclude_thenCorrect() throws JsonProcessingException {
        final MyBean bean = new MyBean(1, null);

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("name")));
    }

    @Test
    public void whenSerializingUsingJsonAutoDetect_thenCorrect() throws JsonProcessingException {
        final PrivateBean bean = new PrivateBean(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("1"));
        assertThat(result, containsString("My bean"));
    }

    // ========================= Polymorphic annotations ============================

    @Test
    public void whenSerializingPolymorphic_thenCorrect() throws JsonProcessingException {
        final Zoo.Dog dog = new Zoo.Dog("lacy");
        final Zoo zoo = new Zoo(dog);

        final String result = new ObjectMapper().writeValueAsString(zoo);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("dog"));
    }

    @Test
    public void whenDeserializingPolymorphic_thenCorrect() throws IOException {
        final String json = "{\"animal\":{\"name\":\"lacy\",\"type\":\"cat\"}}";

        final Zoo zoo = new ObjectMapper().readerFor(Zoo.class)
            .readValue(json);

        assertEquals("lacy", zoo.animal.name);
        assertEquals(Zoo.Cat.class, zoo.animal.getClass());
    }
    // ========================= General annotations ============================

    @Test
    public void whenUsingJsonProperty_thenCorrect() throws IOException {
        final BeanWithGetter bean = new BeanWithGetter(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));

        final BeanWithGetter resultBean = new ObjectMapper().readerFor(BeanWithGetter.class)
            .readValue(result);
        assertEquals("My bean", resultBean.getTheName());
    }

    @Test
    public void whenSerializingUsingJsonFormat_thenCorrect() throws JsonProcessingException, ParseException {
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        final String toParse = "20-12-2014 02:30:00";
        final Date date = df.parse(toParse);
        final EventWithFormat event = new EventWithFormat("party", date);

        final String result = new ObjectMapper().writeValueAsString(event);
        assertThat(result, containsString(toParse));
    }

    @Test
    public void whenSerializingUsingJsonUnwrapped_thenCorrect() throws JsonProcessingException, ParseException {
        final UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        final UnwrappedUser user = new UnwrappedUser(1, name);

        final String result = new ObjectMapper().writeValueAsString(user);
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("name")));
    }

    @Test
    public void whenSerializingUsingJsonView_thenCorrect() throws JsonProcessingException, JsonProcessingException {
        final Item item = new Item(2, "book", "John");

        final String result = new ObjectMapper().writerWithView(Views.Public.class)
            .writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));
        assertThat(result, not(containsString("John")));
    }

    @Test
    public void whenSerializingUsingJacksonReferenceAnnotation_thenCorrect() throws JsonProcessingException {
        final UserWithRef user = new UserWithRef(1, "John");
        final ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }

    @Test
    public void whenSerializingUsingJsonIdentityInfo_thenCorrect() throws JsonProcessingException {
        final UserWithIdentity user = new UserWithIdentity(1, "John");
        final ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
    }

    @Test
    public void whenSerializingUsingJsonFilter_thenCorrect() throws JsonProcessingException {
        final BeanWithFilter bean = new BeanWithFilter(1, "My bean");

        final FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));

        final String result = new ObjectMapper().writer(filters)
            .writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));
    }

    // =========================
    @Test
    public void whenSerializingUsingCustomAnnotation_thenCorrect() throws JsonProcessingException {
        final BeanWithCustomAnnotation bean = new BeanWithCustomAnnotation(1, "My bean", null);

        final String result = new ObjectMapper().writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("dateCreated")));
    }

    // @Ignore("Jackson 2.7.1-1 seems to have changed the API regarding mixins")
    @Test
    public void whenSerializingUsingMixInAnnotation_thenCorrect() throws JsonProcessingException {
        final com.baeldung.jackson.annotation.dtos.Item item = new com.baeldung.jackson.annotation.dtos.Item(1, "book", null);

        String result = new ObjectMapper().writeValueAsString(item);
        assertThat(result, containsString("owner"));

        final ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(com.baeldung.jackson.annotation.dtos.User.class, MyMixInForIgnoreType.class);

        result = mapper.writeValueAsString(item);
        assertThat(result, not(containsString("owner")));
    }

    @Test
    public void whenDisablingAllAnnotations_thenAllDisabled() throws JsonProcessingException {
        final MyBean bean = new MyBean(1, null);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);

        final String result = mapper.writeValueAsString(bean);
        assertThat(result, containsString("1"));
        assertThat(result, containsString("name"));
    }
    
    @Test
    public void whenDeserializingUsingJsonAlias_thenCorrect() throws IOException {

        // arrange
        String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";

        // act
        AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);

        // assert
        assertThat(aliasBean.getFirstName(), is("John"));
    }
    
    @Test
    public void whenSerializingUsingXMLRootNameWithNameSpace_thenCorrect() throws JsonProcessingException {

        // arrange
        UserWithRootNamespace author = new UserWithRootNamespace(1, "John");

        // act
        ObjectMapper mapper = new XmlMapper();
        mapper = mapper.enable(SerializationFeature.WRAP_ROOT_VALUE).enable(SerializationFeature.INDENT_OUTPUT);
        String result = mapper.writeValueAsString(author);

        // assert
        assertThat(result, containsString("<user xmlns=\"users\">"));

        /*
            <user xmlns="users">
              <id xmlns="">3006b44a-cf62-4cfe-b3d8-30dc6c46ea96</id>
              <id xmlns="">1</id>
              <name xmlns="">John</name>
              <items xmlns=""/>
            </user>
        */

    }
    
    

}
