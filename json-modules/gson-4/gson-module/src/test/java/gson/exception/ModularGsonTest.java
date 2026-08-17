package gson.exception;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class ModularGsonTest {

    @Test
    public void givenModularAndExportedPackage_whenDeserializingPojo_thenJsonIOException() {
        String json = "{\"name\":\"Java Conference\",\"numberOfParticipants\":150}";
        final Gson gson = new Gson();

        assertThrows(JsonIOException.class, () -> {
            gson.fromJson(json, ConferencePojo.class);
        });
    }

    @Test
    public void givenModularAndExportedPackage_whenDeserializingRecord_thenSuccess() {
        String json = "{\"name\":\"Java Conference\",\"numberOfParticipants\":150}";
        final Gson gson = new Gson();

        ConferenceRecord result = assertDoesNotThrow(() -> {
            return gson.fromJson(json, ConferenceRecord.class);
        });

        assertNotNull(result);
        assertEquals("Java Conference", result.name());
    }

    @Test
    public void givenModularAndExportedPackage_whenDeserializingPublicPojo_thenSuccess() {
        String json = "{\"name\":\"Java Conference\",\"numberOfParticipants\":150}";
        final Gson gson = new Gson();

        ConferencePojoPublic result = assertDoesNotThrow(() -> {
            return gson.fromJson(json, ConferencePojoPublic.class);
        });

        assertNotNull(result);
        assertEquals("Java Conference", result.name);
    }
}
