package org.baeldung.jsonpath.introduction;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import com.jayway.jsonpath.JsonPath;

public class RegisteringAccountTest {

    enum Result {
        SUCCESS, FAILURE
    }

    InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("intro_user.json");
    String jsonDataSourceString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();

    @Test
    public void givenUseCase_whenRegisteringUnusedAccount_thenSucceed() {
        String unusedRequestBody = "{\"username\":\"jayway\", \"password\":\"JsonPath\"}";
        Result result = registeringNewAccountHelper(unusedRequestBody);

        assertEquals(Result.SUCCESS, result);
    }

    @Test
    public void givenUseCase_whenRegisteringUsedAccount_thenFail() {
        String usedRequestBody = "{\"username\":\"oracle\", \"password\":\"Java_SE_9\"}";
        Result result = registeringNewAccountHelper(usedRequestBody);

        assertEquals(Result.FAILURE, result);
    }

    private Result registeringNewAccountHelper(String requestBody) {
        List<String> userDataSource = JsonPath.parse(jsonDataSourceString).read("$[*]['username']");
        String extractedUsername = JsonPath.parse(requestBody).read("$['username']");

        if (userDataSource.toString().contains(extractedUsername))
            return Result.FAILURE;
        return Result.SUCCESS;
    }
}
