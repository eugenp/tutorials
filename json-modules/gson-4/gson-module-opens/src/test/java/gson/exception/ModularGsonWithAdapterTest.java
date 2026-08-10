package gson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder; // Wymagany nowy import

public class ModularGsonWithAdapterTest {

    @Test
    void whenAdapterForPojo_thenSuccess() {
        final Gson gson = new GsonBuilder().registerTypeAdapter(ConferencePojo.class, new ConferencePojoAdapter())
            .create();

        final String jsonInput = "{\"name\":\"Java Conference\", \"numberOfParticipants\":100}";

        ConferencePojo result = gson.fromJson(jsonInput, ConferencePojo.class);

        assertNotNull(result);
        assertEquals("Java Conference", result.getName());
    }
}
