package com.baeldung.test.comparison;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MyParameterisedUnitTestNg {

    private PrimeNumberCheck primeNumberChecker;

    @BeforeClass
    public void intialSetup() {
        primeNumberChecker = new PrimeNumberCheck();
    }

    @Test(enabled = false)
    @Parameters({ "num", "expectedResult" })
    public void givenNumber_ifPrime_thenCorrect(int number, boolean expectedResult) {
        Assert.assertEquals(expectedResult, primeNumberChecker.validate(number));
    }

    @DataProvider(name = "test1")
    public static Object[][] primeNumbers() {
        return new Object[][] { { 2, true }, { 6, false }, { 19, true }, { 22, false }, { 23, true } };
    }

    @Test(dataProvider = "test1")
    public void givenNumber_whenPrime_thenCorrect(Integer inputNumber, Boolean expectedResult) {
        Assert.assertEquals(expectedResult, primeNumberChecker.validate(inputNumber));
    }

    @Test(dataProvider = "myDataProvider")
    public void parameterCheckTest(User user) {
        Assert.assertEquals("sam", user.getName());
        Assert.assertEquals(12, user.getAge());
    }

    @DataProvider(name = "myDataProvider")
    public Object[][] parameterProvider() {
        User usr = new User();
        usr.setName("sam");
        usr.setAge(12);
        return new Object[][] { { usr } };
    }

}

class PrimeNumberCheck {

    public Object validate(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

}

class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
