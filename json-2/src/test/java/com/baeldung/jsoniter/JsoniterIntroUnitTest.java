package com.baeldung.jsoniter;

import com.baeldung.jsoniter.model.Name;
import com.baeldung.jsoniter.model.Student;
import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.any.Any;

import org.junit.Test;

import static com.jsoniter.ValueType.STRING;
import static org.assertj.core.api.Assertions.assertThat;

public class JsoniterIntroUnitTest {

    @Test
    public void whenParsedUsingBindAPI_thenConvertedToJavaObjectCorrectly() {
        String input = "{\"id\":1,\"name\":{\"firstName\":\"Joe\",\"surname\":\"Blogg\"}}";

        Student student = JsonIterator.deserialize(input, Student.class);

        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getName().getFirstName()).isEqualTo("Joe");
        assertThat(student.getName().getSurname()).isEqualTo("Blogg");
    }

    @Test
    public void givenTypeInJsonFuzzy_whenFieldIsMaybeDecoded_thenFieldParsedCorrectly() {
        String input = "{\"id\":\"1\",\"name\":{\"firstName\":\"Joe\",\"surname\":\"Blogg\"}}";

        Student student = JsonIterator.deserialize(input, Student.class);

        assertThat(student.getId()).isEqualTo(1);
    }

    @Test
    public void whenParsedUsingAnyAPI_thenFieldValueCanBeExtractedUsingTheFieldName() {
        String input = "{\"id\":1,\"name\":{\"firstName\":\"Joe\",\"surname\":\"Blogg\"}}";

        Any any = JsonIterator.deserialize(input);

        assertThat(any.toInt("id")).isEqualTo(1);
        assertThat(any.toString("name", "firstName")).isEqualTo("Joe");
        assertThat(any.toString("name", "surname")).isEqualTo("Blogg");
    }

    @Test
    public void whenParsedUsingAnyAPI_thenFieldValueTypeIsCorrect() {
        String input = "{\"id\":1,\"name\":{\"firstName\":\"Joe\",\"surname\":\"Blogg\"}}";

        Any any = JsonIterator.deserialize(input);

        assertThat(any.get("id").valueType()).isEqualTo(ValueType.NUMBER);
        assertThat(any.get("name").valueType()).isEqualTo(ValueType.OBJECT);
        assertThat(any.get("error").valueType()).isEqualTo(ValueType.INVALID);
    }

    @Test
    public void whenParsedUsingIteratorAPI_thenFieldValuesExtractedCorrectly() throws Exception {
        Name name = new Name();
        String input = "{ \"firstName\" : \"Joe\", \"surname\" : \"Blogg\" }";
        JsonIterator iterator = JsonIterator.parse(input);

        for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
            switch (field) {
                case "firstName":
                    if (iterator.whatIsNext() == ValueType.STRING) {
                        name.setFirstName(iterator.readString());
                    }
                    continue;
                case "surname":
                    if (iterator.whatIsNext() == ValueType.STRING) {
                        name.setSurname(iterator.readString());
                    }
                    continue;
                default:
                    iterator.skip();
            }
        }

        assertThat(name.getFirstName()).isEqualTo("Joe");
        assertThat(name.getSurname()).isEqualTo("Blogg");
    }

}
