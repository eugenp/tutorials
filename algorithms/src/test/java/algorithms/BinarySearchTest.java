import org.junit.Assert;
import org.junit.Test;


public class BinarySearchTest {

    @Test
    public void givenASortedArrayOfIntegers_whenBinarySearchRunForANumber_thenGetIndexOfTheNumber() {
        BinarySearch binSearch = new BinarySearch();
        int expectedIndexForSearchKey = 7;
        Assert.assertEquals(expectedIndexForSearchKey, binSearch.runBinarySearch());
    }

}
