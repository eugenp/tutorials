package gson.exception;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModularStructureConfirmationUnitTest {

    @Test
    void whenModular_thenSuccess() {
        Module module = this.getClass()
            .getModule();
        assertTrue(module.isNamed(), "Test run on Classpath, JPMS strong encapsulation won't work!");
    }

}
