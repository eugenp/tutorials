package com.baeldung.annotations.parser;

import com.baeldung.annotations.AddressService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MethodDescriptionParserUnitTest {

    @Test
    void listMethodDescriptions() {
        List<String> methodNames = MethodDescriptionParser.listMethodDescriptions(AddressService.class);
        assertEquals(3, methodNames.size());
        assertTrue(methodNames.contains("addAddress"));
        assertTrue(methodNames.contains("updateAddress"));
        assertTrue(methodNames.contains("deleteAddress"));
    }
}