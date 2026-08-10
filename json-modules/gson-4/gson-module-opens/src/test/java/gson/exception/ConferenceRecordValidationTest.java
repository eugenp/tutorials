package gson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.gson.Gson;

public class ConferenceRecordValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "{}",                 // Case 1: Empty JSON
        "{\"nazwa\":null}",   // Case 2: Explicit null in JSON
        "{\"nazwa\":\"\"}",   // Case 3: Empty String ""
        "{\"nazwa\":\" \"}"   // Case 4: String contains only space " "
    })
    public void givenGson_WhenRecord_ThenValidation(String badJson) {

        final Gson gson = new Gson();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gson.fromJson(badJson, ConferenceRecordWithValidation.class);
        });

        Throwable rootCause = exception.getCause();
        assertInstanceOf(IllegalArgumentException.class, rootCause);
        assertEquals("The conference name cannot be left blank!", rootCause.getMessage());
    }
}
