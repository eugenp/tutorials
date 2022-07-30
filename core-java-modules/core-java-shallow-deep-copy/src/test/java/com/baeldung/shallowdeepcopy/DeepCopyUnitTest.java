package com.baeldung.shallowdeepcopy;

import static org.junit.Assert.assertNotEquals;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import com.baeldung.pojo.deepcopy.BookDetailDeepCopy;
import com.baeldung.pojo.deepcopy.BookShelfInfoDeepCopy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class DeepCopyUnitTest {

    @Test
    public void whenCreatingDeepCopyWithCloneable_andChangeCloneDate_thenCloneShouldNotBeSameAsOriginalObject() throws CloneNotSupportedException {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoDeepCopy originalObj = new BookShelfInfoDeepCopy(1111, "Head First SQL", "A12", bookDetail);
        // deep copy happens as all the objects with COMPOSITION RELATIONSHIP with this class also implement Cloneable interface
        // and override clone method
        BookShelfInfoDeepCopy deepCopy = (BookShelfInfoDeepCopy) originalObj.clone();
        // 'deepCopy' and 'originalObj' reference completely different memory locations.
        // Thus changing one does not change the other.
        deepCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopy);
    }

    @Test
    public void whenCreatingDeepcopyWithCopyConstructor_andChangeCopiedObject_thenOnlyCopiedObjectShouldChange() {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoDeepCopy originalObj = new BookShelfInfoDeepCopy(1111, "Head First SQL", "A12", bookDetail);
        // create a deep copy through copy constructor
        // Objects having COMPOSITION RELATIONSHIP with this class also implement copy constructor.
        // Hence, actual values are copied.
        BookShelfInfoDeepCopy deepCopy = new BookShelfInfoDeepCopy(originalObj);// triggers
                                                                                // deep
                                                                                // copy
        deepCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopy);
    }

    @Test
    public void whenCreatingDeepCopyWithSerializableUsingJacksonLib_andChangeJacksonCopy_thenOnlyJacksonCopyShouldChange() throws JsonProcessingException {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoDeepCopy originalObj = new BookShelfInfoDeepCopy(1111, "Head First SQL", "A12", bookDetail);

        ObjectMapper objectMapper = new ObjectMapper();
        // Completely separate instance of the type with same values as original is created.
        // All objects having COMPOSITION RELATIONSHIP with this class also have to implement Serializable interface.
        BookShelfInfoDeepCopy deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(originalObj), BookShelfInfoDeepCopy.class);
        // Hence, change in 'deepCopy' does not change 'originalObj' in any way.
        deepCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopy);

    }

    @Test
    public void whenCreatingDeepCopyWithSerializableUsingGsonLib_andChangeGsonCopy_thenOnlyGsonCopyShouldChange() {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoDeepCopy originalObj = new BookShelfInfoDeepCopy(1111, "Head First SQL", "A12", bookDetail);

        Gson gson = new Gson();
        BookShelfInfoDeepCopy deepCopy = gson.fromJson(gson.toJson(originalObj), BookShelfInfoDeepCopy.class);
        deepCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopy);

    }

    @Test
    public void whenCreatingDeepCopyWithSerializableUsingApacheCommonsLang_andChangeCommonsLangCopy_thenOnlyCommonsLangCopyShouldChange() {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoDeepCopy originalObj = new BookShelfInfoDeepCopy(1111, "Head First SQL", "A12", bookDetail);
        BookShelfInfoDeepCopy deepCopy = (BookShelfInfoDeepCopy) SerializationUtils.clone(originalObj);
        deepCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopy);

    }

}
