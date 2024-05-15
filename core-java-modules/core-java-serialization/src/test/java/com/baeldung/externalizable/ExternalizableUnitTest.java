package com.baeldung.externalizable;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ExternalizableUnitTest {

    private final static String OUTPUT_FILE_NAME = "externalizable.txt";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private File outputFile;

    @Before
    public void setUp() throws Exception {
        outputFile = tempFolder.newFile(OUTPUT_FILE_NAME);
    }

    @Test
    public void whenSerializing_thenUseExternalizable() throws IOException, ClassNotFoundException {

        Country c = new Country();
        c.setCapital("Yerevan");
        c.setCode(374);
        c.setName("Armenia");

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        c.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(outputFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Country c2 = new Country();
        c2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertEquals(c2.getCode(), c.getCode());
        assertEquals(c2.getName(), c.getName());
    }

    @Test
    public void whenInheritanceSerialization_then_UseExternalizable() throws IOException, ClassNotFoundException {

        Region r = new Region();
        r.setCapital("Yerevan");
        r.setCode(374);
        r.setName("Armenia");
        r.setClimate("Mediterranean");
        r.setPopulation(120.000);

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        r.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(outputFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Region r2 = new Region();
        r2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertNull(r2.getPopulation());
    }
}
