package com.baeldung.shallowDeepCopyExamples;

import static org.junit.Assert.assertNotEquals;
import java.io.IOException;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import com.baeldung.shallowDeepCopyExamples.pojo.Address;
import com.baeldung.shallowDeepCopyExamples.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class DeepCopyWithLibrariesTest {

    @Test
    public void whenModifyingOriginalObject_thenCommonsCloneShouldNotChange() {
        Address address = new Address("United states");
        User user = new User("Peter", "Parker", 25, address);
        User deepCopy = (User) SerializationUtils.clone(user);

        address.setStreetName("London");

        assertNotEquals(deepCopy.getAddress().getStreetName(), user.getAddress().getStreetName());
    }

    @Test
    public void whenModifyingOriginalObject_thenGsonCloneShouldNotChange() {
        Address address = new Address("United states");
        User user = new User("Peter", "Parker", 25, address);
        Gson gson = new Gson();
        User deepCopy = gson.fromJson(gson.toJson(user), User.class);

        address.setStreetName("London");

        assertNotEquals(deepCopy.getAddress().getStreetName(), user.getAddress().getStreetName());
    }

    @Test
    public void whenModifyingOriginalObject_thenJacksonCopyShouldNotChange()
            throws IOException {
        Address address = new Address("United states");
        User user = new User("Peter", "Parker", 25, address);
        ObjectMapper objectMapper = new ObjectMapper();

        User deepCopy = objectMapper
                .readValue(objectMapper.writeValueAsString(user), User.class);

        address.setStreetName("London");

        assertNotEquals(deepCopy.getAddress().getStreetName(), user.getAddress().getStreetName());
    }
}
