package main.test.com.baeldung.copy;

import main.java.com.baeldung.copy.Company;
import main.java.com.baeldung.copy.ShallowCopyCar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShallowCopyCarTest {

    @Test
    public void testAfterShallowCopyNewObjectShouldHoldSameState() {
        Company mercedes = new Company("Mercedes");
        ShallowCopyCar gClass = new ShallowCopyCar("G-Class", mercedes);
        ShallowCopyCar gClassCopy = new ShallowCopyCar(gClass);

        assertEquals(gClass.getModel(), gClassCopy.getModel());
        assertEquals(gClass.getCompany().getName(), gClassCopy.getCompany().getName());
    }

    @Test
    public void testAfterShallowCopySameCompanyObjectShouldBeReferenced() {
        Company mercedes = new Company("Mercedes");
        ShallowCopyCar gClass = new ShallowCopyCar("G-Class", mercedes);
        ShallowCopyCar gClassCopy = new ShallowCopyCar(gClass);

        assertSame(gClassCopy.getCompany(), gClass.getCompany());
    }
}