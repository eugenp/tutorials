package org.baeldung.filesystem.jndi.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.baeldung.filesystem.jndi.LookupFSJNDI;
import org.junit.Test;

public class LookupFSJNDITest {
    LookupFSJNDI fsjndi;
    File file;
    InitialContext ctx = null;
    final String FILENAME = "test.txt";

    public LookupFSJNDITest() {
        try {
            fsjndi = new LookupFSJNDI();
        } catch (NamingException e) {
            fsjndi = null;
        }
    }

    @Test
    public void whenInitializationLookupFSJNDIIsNotNull_thenSuccess() {
        assertNotNull("Class LookupFSJNDI has instance", fsjndi);
    }

    @Test
    public void givenLookupFSJNDI_whengetInitialContextIsNotNull_thenSuccess() {
        ctx = fsjndi.getCtx();
        assertNotNull("Context exists", ctx);
    }

    @Test
    public void givenInitialContext_whenLokupFileExists_thenSuccess() {
            File file = fsjndi.getFile(FILENAME);
         assertNotNull("File exists", file);
    }
}
