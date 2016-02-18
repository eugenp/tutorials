package org.baeldung.jsonpath.introduction;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Years;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class ChangingPasswordTest {

    enum Result {
        SUCCESS, FAILURE
    }

    InputStream jsonValueInputStream = this.getClass().getClassLoader().getResourceAsStream("intro_user.json");
    String jsonDataSourceString = new Scanner(jsonValueInputStream, "UTF-8").useDelimiter("\\Z").next();

    @Test
    public void givenUseCase_whenChangingPasswordOfNonExistentUser_thenFail() {
        String failedRequestBody = "{\"username\":\"jayway\", \"new_password\":\"JsonPath\"}";
        Result result = changingPasswordHelper(failedRequestBody);

        assertEquals(Result.FAILURE, result);
    }

    @Test
    public void givenUseCase_whenChangingToUnusedPassword_thenSucceed() {
        String successfulRequestBody = "{\"username\":\"oracle\", \"new_password\":\"Java_SE_9\"}";
        Result result = changingPasswordHelper(successfulRequestBody);

        assertEquals(Result.SUCCESS, result);
    }

    @Test
    public void givenUseCase_whenChangingToRecentlyUsedPassword_thenFail() {
        String failedRequestBody = "{\"username\":\"oracle\", \"new_password\":\"Java_SE_7\"}";
        Result result = changingPasswordHelper(failedRequestBody);

        assertEquals(Result.FAILURE, result);
    }

    @Test
    public void givenUseCase_whenChangingToLongTimeAgoPassword_thenSucceed() {
        String successfulRequestBody = "{\"username\":\"sun\", \"new_password\":\"J2SE_5.0\"}";
        Result result = changingPasswordHelper(successfulRequestBody);

        assertEquals(Result.SUCCESS, result);
    }

    private Result changingPasswordHelper(String requestBody) {
        DocumentContext requestContext = JsonPath.parse(requestBody);
        String extractedUsername = requestContext.read("$['username']");
        String extractedPassword = requestContext.read("$['new_password']");
        
        DocumentContext jsonContext = JsonPath.parse(jsonDataSourceString);
        List<String> dataSourceUsername = jsonContext.read("$[?(@.username == '" + extractedUsername + "')]");
        if (dataSourceUsername.size() == 0)
            return Result.FAILURE;

        Configuration pathConfiguration = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> pathToCurrentUser = JsonPath.using(pathConfiguration).parse(jsonDataSourceString).read("$[?(@.username == '" + extractedUsername + "')]");
        List<Long> passwordCreatedTimeList = jsonContext.read(pathToCurrentUser.get(0) + "['password']['old'][?(@.value == '" + extractedPassword + "')]['created']");
        if (passwordCreatedTimeList.size() == 0)
            return Result.SUCCESS;

        Long[] passwordCreatedTimeArray = (passwordCreatedTimeList.toArray(new Long[passwordCreatedTimeList.size()]));
        Arrays.sort(passwordCreatedTimeArray);
        DateTime oldDate = new DateTime(passwordCreatedTimeArray[passwordCreatedTimeArray.length - 1]);
        if (Years.yearsBetween(oldDate, new DateTime()).getYears() <= 10)
            return Result.FAILURE;

        return Result.SUCCESS;
    }
}
