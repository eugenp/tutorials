package gson.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ModularStructureConfirmationTest {

    @Test
    void whenModular_thenSuccess() {
        Module module = this.getClass()
            .getModule();
        assertTrue(module.isNamed(), "Test run on Classpath, JPMS strong encapsulation won't work!");
    }

}
