package com.baeldung.chicory;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.runtime.Store;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import com.dylibso.chicory.wasm.types.FunctionType;
import com.dylibso.chicory.wasm.types.ValType;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChicoryUnitTest {

    @Test
    void givenAddModule_whenCallingAddWithTwoAndForty_thenResultIsFortyTwo() {
        InputStream wasm = getClass().getResourceAsStream("/chicory/add.wasm");
        assertNotNull(wasm);

        WasmModule module = Parser.parse(wasm);
        Instance instance = Instance.builder(module).build();
        ExportFunction add = instance.export("add");

        long[] result = add.apply(2, 40);

        assertEquals(42, (int) result[0]);
    }

    @Test
    void givenImportDouble_whenCallingUseDouble_thenResultIsDoubled() {
        InputStream wasm = getClass().getResourceAsStream("/chicory/imports.wasm");
        assertNotNull(wasm);

        HostFunction doubleFn = new HostFunction(
                "host",
                "double",
                FunctionType.of(List.of(ValType.I32), List.of(ValType.I32)),
                (Instance instance, long... args) -> new long[] { args[0] * 2 }
        );

        Store store = new Store();
        store.addFunction(doubleFn);

        WasmModule module = Parser.parse(wasm);
        Instance instance = store.instantiate("imports", module);
        ExportFunction useDouble = instance.export("useDouble");

        long[] result = useDouble.apply(21);

        assertEquals(42L, result[0]);
    }

    @Test
    void whenInstantiatingModuleWithoutRequiredImport_thenErrorIsThrown() {
        InputStream wasm = getClass().getResourceAsStream("/chicory/imports.wasm");
        assertNotNull(wasm);

        WasmModule module = Parser.parse(wasm);

        assertThrows(RuntimeException.class, () -> {
            Instance.builder(module).build();
        });
    }

    @Test
    void whenRequestingMissingExport_thenErrorIsThrown() {
        InputStream wasm = getClass().getResourceAsStream("/chicory/add.wasm");
        assertNotNull(wasm);

        WasmModule module = Parser.parse(wasm);
        Instance instance = Instance.builder(module).build();

        assertThrows(RuntimeException.class, () -> instance.export("sum"));
    }

}