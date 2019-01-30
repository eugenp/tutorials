package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang.SerializationUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * 深克隆测试
 * 1、浅拷贝：对基本数据类型进行值传递，对引用数据类型进行引用传递般的拷贝，此为浅拷贝。
 * 2、深拷贝：对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容，此为深拷贝。
 */
public class DeepCopyUnitTest {

    @Test
    public void whenCreatingDeepCopyWithCopyConstructor_thenObjectsShouldNotBeSame() {

        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = new User(pm);

        boolean isSame = deepCopy.equals(pm);
        Assert.assertEquals(false , isSame);

        assertThat(deepCopy).isNotSameAs(pm);

        HashMap map = new HashMap();
        HashMap cloneMap = (HashMap) map.clone();
        System.out.println(map == cloneMap);
        System.out.println(map.equals(cloneMap));
    }

    /**
     * @see org.assertj.core.api.Assertions#assertThat(String actual)
     * @see org.assertj.core.api.AbstractAssert#isNotEqualTo(Object other)
     */
    @Test
    public void whenModifyingOriginalObject_thenConstructorCopyShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = new User(pm);

        address.setCountry("Great Britain");

        System.out.println(deepCopy.getAddress().getCountry());
        System.out.println(pm.getAddress().getCountry());

        assertThat(deepCopy.getAddress().getCountry())
                .isNotEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenModifyingOriginalObject_thenCloneCopyShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = (User) pm.clone();

        address.setCountry("Great Britain");

        System.out.println(deepCopy.getAddress().getCountry());
        System.out.println(pm.getAddress().getCountry());

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    /**
     * @see org.apache.commons.lang.SerializationUtils#clone(Serializable object)
     */
    @Test
    public void whenModifyingOriginalObject_thenCommonsCloneShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = (User) SerializationUtils.clone(pm);

        address.setCountry("Great Britain");

        System.out.println(deepCopy.getAddress().getCountry());
        System.out.println(pm.getAddress().getCountry());

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    /**
     * @see com.google.gson.Gson#fromJson(String, Type)
     */
    @Test
    public void whenModifyingOriginalObject_thenGsonCloneShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        Gson gson = new Gson();
        User deepCopy = gson.fromJson(gson.toJson(pm), User.class);

        address.setCountry("Great Britain");

        System.out.println(deepCopy.getAddress().getCountry());
        System.out.println(pm.getAddress().getCountry());

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    /**
     * @see com.fasterxml.jackson.databind.ObjectMapper#readValue(String content, Class valueType)
     * @throws IOException
     */
    @Test
    public void whenModifyingOriginalObject_thenJacksonCopyShouldNotChange() throws IOException {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        ObjectMapper objectMapper = new ObjectMapper();
        User deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(pm), User.class);

        address.setCountry("Great Britain");

        System.out.println(deepCopy.getAddress().getCountry());
        System.out.println(pm.getAddress().getCountry());

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    /**
     * 几种常用克隆方法的效率：
     * @see DeepCopyUnitTest#whenModifyingOriginalObject_thenCommonsCloneShouldNotChange()
     * @see DeepCopyUnitTest#whenModifyingOriginalObject_thenGsonCloneShouldNotChange()
     * @see DeepCopyUnitTest#whenModifyingOriginalObject_thenConstructorCopyShouldNotChange()
     * @see DeepCopyUnitTest#whenModifyingOriginalObject_thenCloneCopyShouldNotChange()
     * @see DeepCopyUnitTest#whenModifyingOriginalObject_thenJacksonCopyShouldNotChange()
     *
     * @throws CloneNotSupportedException
     * @throws IOException
     */
    @Test
    @Ignore
    public void whenMakingCopies_thenShowHowLongEachMethodTakes() throws CloneNotSupportedException, IOException {
        int times = 1000000;
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);

        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            User primeMinisterClone = (User) SerializationUtils.clone(pm);
        }
        long end = System.currentTimeMillis();
        System.out.println("Cloning with Apache Commons Lang took " + (end - start) + " milliseconds.");

        start = System.currentTimeMillis();
        Gson gson = new Gson();
        for (int i = 0; i < times; i++) {
            User primeMinisterClone = gson.fromJson(gson.toJson(pm), User.class);
        }
        end = System.currentTimeMillis();
        System.out.println("Cloning with Gson took " + (end - start) + " milliseconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            User primeMinisterClone = new User(pm);
        }
        end = System.currentTimeMillis();
        System.out.println("Cloning with the copy constructor took " + (end - start) + " milliseconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            User primeMinisterClone = (User) pm.clone();
        }
        end = System.currentTimeMillis();
        System.out.println("Cloning with Cloneable interface took " + (end - start) + " milliseconds.");

        start = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < times; i++) {
            User primeMinisterClone = objectMapper.readValue(objectMapper.writeValueAsString(pm), User.class);
        }
        end = System.currentTimeMillis();
        System.out.println("Cloning with Jackson took " + (end - start) + " milliseconds.");
    }
}
