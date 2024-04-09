package java.com.baeldung.objectcopy;

import org.junit.Test;

import static org.junit.Assert.*;

public class HouseShallowTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {

        PostOfficeShallow originalPostOffice = new PostOfficeShallow("Original Post Office");
        originalPostOffice.setZipCode("01033");
        HouseShallow originalHouse = new HouseShallow("Original House", "Kyiv, Saksaganskogo 17", originalPostOffice);

        HouseShallow copiedHouse = (HouseShallow) originalHouse.clone();

        assertNotSame("The copied house should not be the same object as the original", originalHouse, copiedHouse);
        assertSame("The post office in both the original and copied house should be the same", originalHouse.getPostOffice(), copiedHouse.getPostOffice());

        copiedHouse.getPostOffice().setName("Modified Post Office");

        assertEquals("Modified Post Office", originalHouse.getPostOffice().getName());
        assertEquals("Modified Post Office", copiedHouse.getPostOffice().getName());
    }
}
