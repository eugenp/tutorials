package gson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

class PojoWithLocalDateUnitTest {

    @Test
    void whenObjectDateFormat_thenSuccessfulDeserialization() {
        String correctJson = "{" 
          + "\"name\":\"Java Conference\"," 
          + "\"numberOfParticipants\":500," 
          + "\"conferenceStart\":{\"year\":2026,\"month\":8,\"day\":17}"
          + "}";

        Gson gson = new Gson();
        ConferencePojoWithDate result = gson.fromJson(correctJson, ConferencePojoWithDate.class);

        LocalDate excpectedDate = LocalDate.of(2026, 8, 17);
        assertEquals(excpectedDate, result.getConferenceStart(), "Date should be the same as in JSON");
    }

    @Test
    void whenISOTextFormat_thenJsonSyntaxException() {

        String wrongDateInJson = "{"
          + "\"name\":\"Java Conference\"," 
          + "\"numberOfParticipants\":500,"
          + "\"conferenceStart\":\"2026-08-17\""
          + "}";

        Gson gson = new Gson();
        assertThrows(JsonSyntaxException.class, () -> {
            gson.fromJson(wrongDateInJson, ConferencePojoWithDate.class);
        }, "JsonSyntaxException was expected due to an incompatible date format");

    }
}
