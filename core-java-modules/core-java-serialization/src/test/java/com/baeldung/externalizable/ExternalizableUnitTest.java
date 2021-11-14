package com.baeldung.externalizable;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;

public class ExternalizableUnitTest {

    private final static String OUTPUT_FILE = "externalizable.txt";

    @Test
    public void whenSerializing_thenUseExternalizable() throws IOException, ClassNotFoundException {

        Country c = new Country();
        c.setCapital("Yerevan");
        c.setCode(374);
        c.setName("Armenia");

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        c.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Country c2 = new Country();
        c2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertTrue(c2.getCode() == c.getCode());
        assertTrue(c2.getName().equals(c.getName()));
    }

    @Test
    public void whenInheritanceSerialization_then_UseExternalizable() throws IOException, ClassNotFoundException {

        Region r = new Region();
        r.setCapital("Yerevan");
        r.setCode(374);
        r.setName("Armenia");
        r.setClimate("Mediterranean");
        r.setPopulation(120.000);

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        r.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Region r2 = new Region();
        r2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertTrue(r2.getPopulation() == null);
    }
}
