package com.baeldung.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

public class MyClazzUnitTest {
    final String APACHE = "Apache";
    final String GSON = "Gson";

    @Test
    public void shallowCopyTest() {
        // given
        MyClazz_ original = newMyClazz();
        MyClazz_ shallowCopied = original.shallowCopy();

        // when
        original.getProperties()
          .put("key2", "value2");

        // then
        Assertions.assertEquals(original.getProperties()
          .get("key2"), shallowCopied.getProperties()
          .get("key2"));

        Assertions.assertEquals(original.getProperties()
          .hashCode(), shallowCopied.getProperties()
          .hashCode());

    }

    @Test
    public void deepCopyAcrossCommonsLibraryTest() {
        // given
        MyClazz_ original = newMyClazz();
        MyClazz_ deepCopied = original.deepCopy();

        // when
        original.getProperties()
          .put("key2", "value2");

        //then
        Assertions.assertNotEquals(original.getProperties()
          .hashCode(), deepCopied.getProperties()
          .hashCode());
        Assertions.assertNull(deepCopied.getProperties()
          .get("key2"));

    }

    @Test
    public void deepCopyAcrossConstructorTest() {
        //given
        MyClazz_ myClazz = newMyClazz();
        MyClazz_ myClazzCopy = new MyClazz_(myClazz);

        //when
        myClazz.getProperties()
          .put("key2", "value2");

        //then
        Assertions.assertNull(myClazzCopy.getProperties()
          .get("key2"));
    }

    @Test
    public void deepCopyAcrossGsonTest() {
        //given
        MyClazz_ myClazz = newMyClazz();
        Gson gson = new Gson();

        MyClazz_ myclazzCopy = new Gson().fromJson(gson.toJson(myClazz), MyClazz_.class);

        //when
        myClazz.getProperties()
          .put("key2", "value2");

        //then
        Assertions.assertNull(myclazzCopy.getProperties()
          .get("key2"));
    }

    @Test
    public void gsonAndApacheTimeConsumingTest() {
        //given
        MyClazz_ myClazz = newMyClazz();
        Gson gson = new Gson();

        //when
        long startTime = System.currentTimeMillis();
        MyClazz_ myclazzCopy = new Gson().fromJson(gson.toJson(myClazz), MyClazz_.class);
        long elapsedTimeGson = calculate(System.currentTimeMillis(), startTime, GSON);

        //when
        startTime = System.currentTimeMillis();
        MyClazz_ deepCopied = myClazz.deepCopy();
        long elapsedTimeApache = calculate(System.currentTimeMillis(), startTime, APACHE);

        //then
        Assertions.assertTrue(elapsedTimeGson < elapsedTimeApache);
    }

    private long calculate(long stopTime, long startTime, String method) {
        long elapsedTime = stopTime - startTime;

        System.out.println(method + " : " + elapsedTime);

        return stopTime - startTime;

    }

    private static MyClazz_ newMyClazz() {
        Map<String, String> properties = new LinkedHashMap<>();
        properties.put("key1", "value1");

        return new MyClazz_(properties);
    }

}