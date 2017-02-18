package baeldung.com;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametrizedTestNGTest {

    @Test
    @Parameters({ "name", "expectedResult" })
    public void givenNumber_ifPrime_thenCorrect(String name, boolean expectedResult) {
        Assert.assertEquals(expectedResult, name.length() > 0);
    }

    @DataProvider(name = "test1")
    public static Object[][] primeNumbers() {
        return new Object[][] { { "Peter", true }, { "Sam", true }, { "Tim", true }, { "Lucy", true } };
    }

    @Test(dataProvider = "test1")
    public void givenNumber_whenPrime_thenCorrect(String name, boolean expectedResult) {
        Assert.assertEquals(expectedResult, name.length() > 0);
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
