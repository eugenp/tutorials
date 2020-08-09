package com.baeldung.maven.plugins;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.baeldung.maven.plugins.Data;

public class DataCheck {
    @Test
    public void whenDataObjectIsCreated_thenItIsNotNull() {
        Data data = new Data();
        assertNotNull(data);
    }
}
