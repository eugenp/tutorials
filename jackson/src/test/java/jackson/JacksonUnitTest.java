package jackson;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUnitTest {

    // tests

    @Test
    public final void whenDtoHasOnlyDefaultValues_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String fooDtoAsString = mapper.writeValueAsString(new FooDto());
        System.out.println(fooDtoAsString);
    }

    @Test
    public final void whenDtoHasNonDefaultValue_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final FooDto dtoObject = new FooDto();
        dtoObject.setBooleanValue(true);

        final String fooDtoAsString = mapper.writeValueAsString(dtoObject);
        System.out.println(fooDtoAsString);
    }

}

/*
Article Ideas:
- Deserializing with a custom JsonParser

 */
