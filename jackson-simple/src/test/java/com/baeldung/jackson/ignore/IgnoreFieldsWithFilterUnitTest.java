package com.baeldung.jackson.ignore;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.FilterProvider;
import tools.jackson.databind.ser.PropertyFilter;
import tools.jackson.databind.ser.PropertyWriter;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class IgnoreFieldsWithFilterUnitTest {

    @Test
    public final void givenTypeHasFilterThatIgnoresFieldByName_whenDtoIsSerialized_thenCorrect() throws StreamReadException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("intValue");
        final FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);

        final MyDtoWithFilter dtoObject = new MyDtoWithFilter();
        dtoObject.setIntValue(12);

        final String dtoAsString = mapper.writer(filters)
            .writeValueAsString(dtoObject);

        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenTypeHasFilterThatIgnoresNegativeInt_whenDtoIsSerialized_thenCorrect() throws StreamReadException, IOException {
        final PropertyFilter theFilter = new SimpleBeanPropertyFilter() {
            @Override
            public final void serializeAsProperty(final Object pojo, final JsonGenerator jgen, final SerializationContext provider, final PropertyWriter writer) throws Exception {
                if (include(writer)) {
                    if (!writer.getName()
                        .equals("intValue")) {
                        writer.serializeAsProperty(pojo, jgen, provider);
                        return;
                    }

                    final int intValue = ((MyDtoWithFilter) pojo).getIntValue();
                    if (intValue >= 0) {
                        writer.serializeAsProperty(pojo, jgen, provider);
                    }
                } else if (!jgen.canOmitProperties()) { // since 2.3
                    writer.serializeAsOmittedProperty(pojo, jgen, provider);
                }
            }

            @Override
            protected final boolean include(final BeanPropertyWriter writer) {
                return true;
            }

            @Override
            protected final boolean include(final PropertyWriter writer) {
                return true;
            }
        };
        final FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);

        final MyDtoWithFilter dtoObject = new MyDtoWithFilter();
        dtoObject.setIntValue(-1);

        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writer(filters)
            .writeValueAsString(dtoObject);

        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        System.out.println(dtoAsString);
    }

}
