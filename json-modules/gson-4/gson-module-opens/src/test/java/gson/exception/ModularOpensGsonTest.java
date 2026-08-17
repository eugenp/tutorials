package gson.exception;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

class ModularOpensGsonTest {

    @Test
    void givenModularAndOpens_whenDeserializingPojo_thenSuccess() {
        String json = "{\"name\":\"Java Conference\",\"numberOfParticipants\":150}";
        Gson gson = new Gson();

        ConferencePojo result = assertDoesNotThrow(() -> {
            return gson.fromJson(json, ConferencePojo.class);
        });

        assertNotNull(result);
        assertEquals("Java Conference", result.getName());
    }

}
