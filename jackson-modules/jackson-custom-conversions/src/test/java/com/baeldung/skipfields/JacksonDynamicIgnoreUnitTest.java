package com.baeldung.skipfields;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class JacksonDynamicIgnoreUnitTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        mapper.registerModule(new SimpleModule() {
            @Override
            public void setupModule(final SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(new BeanSerializerModifier() {
                    @Override
                    public JsonSerializer<?> modifySerializer(final SerializationConfig config, final BeanDescription beanDesc, final JsonSerializer<?> serializer) {
                        if (Hidable.class.isAssignableFrom(beanDesc.getBeanClass())) {
                            return new HidableSerializer((JsonSerializer<Object>) serializer);
                        }
                        return serializer;
                    }
                });
            }
        });
    }

    @Test
    public void whenNotHidden_thenCorrect() throws JsonProcessingException {
        final Address ad = new Address("ny", "usa", false);
        final Person person = new Person("john", ad, false);
        final String result = mapper.writeValueAsString(person);

        assertTrue(result.contains("name"));
        assertTrue(result.contains("john"));
        assertTrue(result.contains("address"));
        assertTrue(result.contains("usa"));
    }

    @Test
    public void whenAddressHidden_thenCorrect() throws JsonProcessingException {
        final Address ad = new Address("ny", "usa", true);
        final Person person = new Person("john", ad, false);
        final String result = mapper.writeValueAsString(person);

        assertTrue(result.contains("name"));
        assertTrue(result.contains("john"));
        assertFalse(result.contains("address"));
        assertFalse(result.contains("usa"));
    }

    @Test
    public void whenAllHidden_thenCorrect() throws JsonProcessingException {
        final Address ad = new Address("ny", "usa", false);
        final Person person = new Person("john", ad, true);
        final String result = mapper.writeValueAsString(person);

        assertTrue(result.length() == 0);
    }

    @Test
    public void whenSerializeList_thenCorrect() throws JsonProcessingException {
        final Address ad1 = new Address("tokyo", "jp", true);
        final Address ad2 = new Address("london", "uk", false);
        final Address ad3 = new Address("ny", "usa", false);
        final Person p1 = new Person("john", ad1, false);
        final Person p2 = new Person("tom", ad2, true);
        final Person p3 = new Person("adam", ad3, false);

        final String result = mapper.writeValueAsString(Arrays.asList(p1, p2, p3));
    }
}
