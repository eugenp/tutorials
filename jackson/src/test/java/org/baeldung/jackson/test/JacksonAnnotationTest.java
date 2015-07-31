package org.baeldung.jackson.test;

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

import org.baeldung.jackson.annotation.BeanWithCreator;
import org.baeldung.jackson.annotation.BeanWithCustomAnnotation;
import org.baeldung.jackson.annotation.BeanWithFilter;
import org.baeldung.jackson.annotation.BeanWithGetter;
import org.baeldung.jackson.annotation.BeanWithIgnore;
import org.baeldung.jackson.annotation.BeanWithInject;
import org.baeldung.jackson.annotation.ExtendableBean;
import org.baeldung.jackson.annotation.MyBean;
import org.baeldung.jackson.annotation.PrivateBean;
import org.baeldung.jackson.annotation.RawBean;
import org.baeldung.jackson.annotation.UnwrappedUser;
import org.baeldung.jackson.annotation.UserWithIgnoreType;
import org.baeldung.jackson.annotation.Zoo;
import org.baeldung.jackson.bidirection.ItemWithIdentity;
import org.baeldung.jackson.bidirection.ItemWithRef;
import org.baeldung.jackson.bidirection.UserWithIdentity;
import org.baeldung.jackson.bidirection.UserWithRef;
import org.baeldung.jackson.date.EventWithFormat;
import org.baeldung.jackson.date.EventWithSerializer;
import org.baeldung.jackson.dtos.MyMixInForString;
import org.baeldung.jackson.dtos.User;
import org.baeldung.jackson.dtos.withEnum.TypeEnumWithValue;
import org.baeldung.jackson.exception.UserWithRoot;
import org.baeldung.jackson.jsonview.Item;
import org.baeldung.jackson.jsonview.Views;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JacksonAnnotationTest {

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
    public void whenSerializingUsingJsonValue_thenCorrect() throws JsonParseException, IOException {
        final String enumAsString = new ObjectMapper().writeValueAsString(TypeEnumWithValue.TYPE1);

        assertThat(enumAsString, is("\"Type A\""));
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
    public void whenDeserializingUsingJsonCreator_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"id\":1,\"theName\":\"My bean\"}";

        final BeanWithCreator bean = new ObjectMapper().reader(BeanWithCreator.class).readValue(json);
        assertEquals("My bean", bean.name);
    }

    @Test
    public void whenDeserializingUsingJsonInject_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"name\":\"My bean\"}";
        final InjectableValues inject = new InjectableValues.Std().addValue(int.class, 1);

        final BeanWithInject bean = new ObjectMapper().reader(inject).withType(BeanWithInject.class).readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals(1, bean.id);
    }

    @Test
    public void whenDeserializingUsingJsonAnySetter_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

        final ExtendableBean bean = new ObjectMapper().reader(ExtendableBean.class).readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals("val2", bean.getProperties().get("attr2"));
    }

    @Test
    public void whenDeserializingUsingJsonSetter_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"id\":1,\"name\":\"My bean\"}";

        final BeanWithGetter bean = new ObjectMapper().reader(BeanWithGetter.class).readValue(json);
        assertEquals("My bean", bean.getTheName());
    }

    @Test
    public void whenDeserializingUsingJsonDeserialize_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"name\":\"party\",\"eventDate\":\"20-12-2014 02:30:00\"}";

        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        final EventWithSerializer event = new ObjectMapper().reader(EventWithSerializer.class).readValue(json);
        assertEquals("20-12-2014 02:30:00", df.format(event.eventDate));
    }

    // ========================= Inclusion annotations ============================

    @Test
    public void whenSerializingUsingJsonIgnoreProperties_thenCorrect() throws JsonProcessingException, IOException {
        final BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));
    }

    @Test
    public void whenSerializingUsingJsonIgnore_thenCorrect() throws JsonProcessingException, IOException {
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
    public void whenSerializingUsingJsonInclude_thenCorrect() throws JsonProcessingException, IOException {
        final MyBean bean = new MyBean(1, null);

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("name")));
    }

    @Test
    public void whenSerializingUsingJsonAutoDetect_thenCorrect() throws JsonProcessingException, IOException {
        final PrivateBean bean = new PrivateBean(1, "My bean");

        final String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("1"));
        assertThat(result, containsString("My bean"));
    }

    // ========================= Polymorphic annotations ============================

    @Test
    public void whenSerializingPolymorphic_thenCorrect() throws JsonProcessingException, IOException {
        final Zoo.Dog dog = new Zoo.Dog("lacy");
        final Zoo zoo = new Zoo(dog);

        final String result = new ObjectMapper().writeValueAsString(zoo);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("dog"));
    }

    @Test
    public void whenDeserializingPolymorphic_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"animal\":{\"name\":\"lacy\",\"type\":\"cat\"}}";

        final Zoo zoo = new ObjectMapper().reader().withType(Zoo.class).readValue(json);

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

        final BeanWithGetter resultBean = new ObjectMapper().reader(BeanWithGetter.class).readValue(result);
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
    public void whenSerializingUsingJsonView_thenCorrect() throws JsonProcessingException {
        final Item item = new Item(2, "book", "John");

        final String result = new ObjectMapper().writerWithView(Views.Public.class).writeValueAsString(item);

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

        final String result = new ObjectMapper().writer(filters).writeValueAsString(bean);

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

    @Test
    public void whenSerializingUsingMixInAnnotation_thenCorrect() throws JsonProcessingException {
        final User user = new User(1, "John");

        String result = new ObjectMapper().writeValueAsString(user);
        assertThat(result, containsString("John"));

        final ObjectMapper mapper = new ObjectMapper();
        mapper.addMixInAnnotations(String.class, MyMixInForString.class);

        result = mapper.writeValueAsString(user);
        assertThat(result, not(containsString("John")));
    }

    @Test
    public void whenDisablingAllAnnotations_thenAllDisabled() throws JsonProcessingException, IOException {
        final MyBean bean = new MyBean(1, null);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);

        final String result = mapper.writeValueAsString(bean);
        assertThat(result, containsString("1"));
        assertThat(result, containsString("name"));
    }

}
