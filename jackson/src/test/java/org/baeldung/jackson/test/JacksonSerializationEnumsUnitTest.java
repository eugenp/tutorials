package org.baeldung.jackson.test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.baeldung.jackson.dtos.withEnum.MyDtoWithEnum;
import org.baeldung.jackson.dtos.withEnum.MyDtoWithEnumCustom;
import org.baeldung.jackson.dtos.withEnum.TypeEnum;
import org.baeldung.jackson.dtos.withEnum.TypeEnumSimple;
import org.baeldung.jackson.dtos.withEnum.TypeEnumWithCustomSerializer;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializationEnumsUnitTest {

    // tests - enums

    @Test
    public final void whenSerializingASimpleEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(TypeEnumSimple.TYPE1.name());
        System.out.println(dtoAsString);

        assertThat(dtoAsString, containsString("TYPE1"));
    }

    @Test
    public final void whenSerializingAnEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(TypeEnum.TYPE1.name());

        System.out.println(dtoAsString);
        assertThat(dtoAsString, containsString("\"name\":\"Type A\""));
    }

    @Test
    public final void whenSerializingEntityWithEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(new MyDtoWithEnum("a", 1, true, TypeEnum.TYPE1));

        System.out.println(dtoAsString);
        assertThat(dtoAsString, containsString("\"name\":\"Type A\""));
    }

    @Test
    public final void givenCustomSerializer_whenSerializingEntityWithEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(new MyDtoWithEnumCustom("a", 1, true, TypeEnumWithCustomSerializer.TYPE1));

        System.out.println(dtoAsString);
        assertThat(dtoAsString, containsString("\"name\":\"Type A\""));
    }

    @Test
    public final void whenSerializingArrayOfEnums_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(new TypeEnum[] { TypeEnum.TYPE1, TypeEnum.TYPE2 });

        System.out.println(json);
        assertThat(json, containsString("\"name\":\"Type A\""));
    }

}
