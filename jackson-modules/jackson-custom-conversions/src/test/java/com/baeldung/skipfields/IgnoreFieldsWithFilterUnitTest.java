package com.baeldung.skipfields;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class IgnoreFieldsWithFilterUnitTest {

    @Test
    public final void givenTypeHasFilterThatIgnoresFieldByName_whenDtoIsSerialized_thenCorrect() throws JsonParseException, IOException {
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
    }

    @Test
    public final void givenTypeHasFilterThatIgnoresNegativeInt_whenDtoIsSerialized_thenCorrect() throws JsonParseException, IOException {
        final PropertyFilter theFilter = new SimpleBeanPropertyFilter() {
            @Override
            public final void serializeAsField(final Object pojo, final JsonGenerator jgen, final SerializerProvider provider, final PropertyWriter writer) throws Exception {
                if (include(writer)) {
                    if (!writer.getName()
                        .equals("intValue")) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }

                    final int intValue = ((MyDtoWithFilter) pojo).getIntValue();
                    if (intValue >= 0) {
                        writer.serializeAsField(pojo, jgen, provider);
                    }
                } else if (!jgen.canOmitFields()) { // since 2.3
                    writer.serializeAsOmittedField(pojo, jgen, provider);
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
    }

}
