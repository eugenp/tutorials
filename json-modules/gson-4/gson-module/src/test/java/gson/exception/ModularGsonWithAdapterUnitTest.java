package gson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class ModularGsonWithAdapterUnitTest {

    @Test
    void whenAdapterForPojo_thenSuccess() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(ConferencePojo.class, new ConferencePojoAdapter())
            .create();

        String json = """
            {
                "name": "Java Conference",
                "numberOfParticipants": 100
            }
            """;

        ConferencePojo result = gson.fromJson(json, ConferencePojo.class);

        assertNotNull(result);
        assertEquals("Java Conference", result.getName());
    }
}
