package com.baeldung.jsonpath.introduction;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class LoggingInTest {

    enum Result {
        SUCCESS, FAILURE
    }

    InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("intro_user.json");
    String jsonDataSourceString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();

    @Test
    public void givenUseCase_whenLoggingInWithCorrectUserData_thenSucceed() {
        String correctRequestBody = "{\"username\":\"sun\", \"password\":\"Java_SE_6\"}";
        Result result = loggingInHelper(correctRequestBody);

        assertEquals(Result.SUCCESS, result);
    }

    @Test
    public void givenUseCase_whenLoggingInWithIncorrectUserData_thenFail() {
        String incorrectRequestBody = "{\"username\":\"oracle\", \"password\":\"Java_SE_9\"}";
        Result result = loggingInHelper(incorrectRequestBody);

        assertEquals(Result.FAILURE, result);
    }

    private Result loggingInHelper(String requestBody) {
        DocumentContext requestContext = JsonPath.parse(requestBody);
        String extractedUsername = requestContext.read("$['username']");
        String extractedPassword = requestContext.read("$['password']");
        List<String> list = JsonPath.parse(jsonDataSourceString).read("$[?(@.username == '" + extractedUsername + "' && @.password.current.value == '" + extractedPassword + "')]");

        if (list.size() == 0)
            return Result.FAILURE;
        return Result.SUCCESS;
    }

}
