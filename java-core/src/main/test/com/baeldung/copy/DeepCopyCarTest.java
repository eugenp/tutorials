package main.test.com.baeldung.copy;

import main.java.com.baeldung.copy.Company;
import main.java.com.baeldung.copy.DeepCopyCar;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DeepCopyCarTest {

    @Test
    public void testAfterDeepCopyNewObjectShouldHoldSameState() {
        Company mercedes = new Company("Mercedes");
        DeepCopyCar gClass = new DeepCopyCar("G-Class", mercedes);
        DeepCopyCar gClassCopy = new DeepCopyCar(gClass);

        assertEquals(gClass.getModel(), gClassCopy.getModel());
        assertEquals(gClass.getCompany().getName(), gClassCopy.getCompany().getName());
    }

    @Test
    public void testAfterDeepCopyNewCompanyObjectShouldBeReferenced() {
        Company mercedes = new Company("Mercedes");
        DeepCopyCar gClass = new DeepCopyCar("G-Class", mercedes);
        DeepCopyCar gClassCopy = new DeepCopyCar(gClass);

        assertNotSame(gClassCopy.getCompany(), gClass.getCompany());
    }
}