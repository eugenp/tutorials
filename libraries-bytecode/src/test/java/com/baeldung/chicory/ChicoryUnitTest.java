package com.baeldung.chicory;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasm.Parser;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChicoryUnitTest {

    @Test
    void givenAddModule_whenCallingAddWithTwoAndForty_thenResultIsFortyTwo() {
        InputStream wasm = getClass().getResourceAsStream("/wasm/add.wasm");
        assertNotNull(wasm);

        var module = Parser.parse(wasm);
        Instance instance = Instance.builder(module).build();
        ExportFunction add = instance.export("add");

        long[] result = add.apply(2, 40);

        assertEquals(42, (int) result[0]);
    }
}
