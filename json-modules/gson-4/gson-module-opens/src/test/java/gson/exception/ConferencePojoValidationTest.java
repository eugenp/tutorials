package gson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.gson.Gson;

public class ConferencePojoValidationTest {

    private final Gson gson = new Gson();

    @Test
    public void givenGson_whenEmptyJson_ThenSetNull() {
        final Gson gson = new Gson();

        String emptyJson = "{}";

        ConferencePojoWithValidation result = gson.fromJson(emptyJson, ConferencePojoWithValidation.class);

        assertNotNull(result);
        assertNull(result.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "{\"name\":\"\"}",   // Empty String
        "{\"name\":\" \"}"   // Space only
    })
    public void givenGson_whenEmptyString_ThenNoValidation(String badJson) {
        final Gson gson = new Gson();

        ConferencePojoWithValidation result = gson.fromJson(badJson, ConferencePojoWithValidation.class);

        assertNotNull(result);

        if (badJson.contains("\" \"")) {
            assertEquals(" ", result.getName());
        } else {
            assertEquals("", result.getName());
        }
    }
}
