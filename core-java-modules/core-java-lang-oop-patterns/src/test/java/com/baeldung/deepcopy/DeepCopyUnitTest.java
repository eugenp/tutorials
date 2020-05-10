package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class DeepCopyUnitTest {

    @Test
    public void whenCreatingDeepCopyWithCopyConstructor_thenObjectsShouldNotBeSame() {

        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);

        User deepCopy = new User(pm);

        assertThat(deepCopy).isNotSameAs(pm);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = new User(pm);

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenModifyingOriginalObject_thenCloneCopyShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = (User) pm.clone();

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenModifyingOriginalObject_thenCommonsCloneShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User deepCopy = (User) SerializationUtils.clone(pm);

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenModifyingOriginalObject_thenGsonCloneShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        Gson gson = new Gson();
        User deepCopy = gson.fromJson(gson.toJson(pm), User.class);

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenModifyingOriginalObject_thenJacksonCopyShouldNotChange() throws IOException {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        ObjectMapper objectMapper = new ObjectMapper();
        User deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(pm), User.class);

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

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
    
    @Test
    public void whenModifyingOriginalObject_ThenCopyShouldChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User shallowCopy = new User(pm.getFirstName(), pm.getLastName(), pm.getAddress());
        address.setCountry("Great Britain");
        assertThat(shallowCopy.getAddress().getCountry()).isEqualTo(pm.getAddress().getCountry());
    }
}
