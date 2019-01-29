package com.baeldung.externalizable;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;

/**
 * 外部化的单元测试
 */
public class ExternalizableUnitTest {

    private final static String OUTPUT_FILE = "externalizable.txt";

    @Test
    public void whenSerializing_thenUseExternalizable() throws IOException, ClassNotFoundException {

        Country c = new Country();
        c.setCapital("Yerevan");
        c.setCode(374);
        c.setName("Armenia");

        //对象序列化输出
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        c.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();


        FileInputStream fileInputStream = new FileInputStream(OUTPUT_FILE);
        //对象序列化输入
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Country c2 = new Country();
        c2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        Assert.assertTrue(c2.getCode() == c.getCode());
        Assert.assertTrue(c2.getName().equals(c.getName()));
    }

    /**
     * 注意：population的类型为Double，在序列化后，将会丢失。
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void whenInheritanceSerialization_then_UseExternalizable() throws IOException, ClassNotFoundException {
        Region r = new Region();
        r.setName("Armenia");
        r.setCapital("Yerevan");
        r.setCode(374);
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

        Assert.assertTrue("Yerevan".equals(r2.getCapital()));
        Assert.assertTrue(374 == r2.getCode());
        Assert.assertTrue("Armenia".equals(r2.getName()));
        Assert.assertTrue("Mediterranean".equals(r2.getClimate()));
        Assert.assertTrue(r2.getPopulation() == null);
    }
}
