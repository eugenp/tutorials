package com.baeldung.shallowdeepcopy;

import static org.junit.Assert.assertNotEquals;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import com.baeldung.pojo.deepcopy.BookDeepCopy;
import com.baeldung.pojo.deepcopy.BookDetailDeepCopy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class DeepCopyUnitTest {

    @Test
    public void whenCloneChanges_thenOriginalObjectShouldNotChange() throws CloneNotSupportedException {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy(2021);
        BookDeepCopy originalObj = new BookDeepCopy("Head First SQL", bookDetail);

        BookDeepCopy deepCopyObj = (BookDeepCopy) originalObj.clone();

        deepCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopyObj);
    }

    @Test
    public void whenCopyChanges_thenOriginalObjectShouldNotChange() {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy(2021);
        BookDeepCopy originalObj = new BookDeepCopy("Head First SQL", bookDetail);

        BookDeepCopy deepCopyObj = new BookDeepCopy(originalObj);

        deepCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopyObj);
    }

    @Test
    public void whenCreatingCopyWithSerializableUsingJacksonLib_thenOriginalObjectShouldNotChange() throws JsonProcessingException {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy(2021);
        BookDeepCopy originalObj = new BookDeepCopy("Head First SQL", bookDetail);

        ObjectMapper objectMapper = new ObjectMapper();

        BookDeepCopy deepCopyObj = objectMapper.readValue(objectMapper.writeValueAsString(originalObj), BookDeepCopy.class);

        deepCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopyObj);

    }

    @Test
    public void whenCreatingCopyWithSerializableUsingGsonLib_thenOriginalObjectShouldNotChange() {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy(2021);
        BookDeepCopy originalObj = new BookDeepCopy("Head First SQL", bookDetail);

        Gson gson = new Gson();
        BookDeepCopy deepCopyObj = gson.fromJson(gson.toJson(originalObj), BookDeepCopy.class);
        deepCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopyObj);

    }

    @Test
    public void whenCreatingCopyWithSerializableUsingApacheCommonsLang_thenOriginalObjectShouldNotChange() {

        BookDetailDeepCopy bookDetail = new BookDetailDeepCopy(2021);
        BookDeepCopy originalObj = new BookDeepCopy("Head First SQL", bookDetail);
        BookDeepCopy deepCopyObj = (BookDeepCopy) SerializationUtils.clone(originalObj);
        deepCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertNotEquals(originalObj, deepCopyObj);

    }

}
