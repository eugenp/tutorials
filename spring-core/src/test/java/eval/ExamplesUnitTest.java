package eval;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Examples.class)
public class ExamplesUnitTest {
    @Inject
    private FieldGarage fieldGarage;
    @Inject
    private MethodGarage methodGarage;
    @Inject
    private ConstructorGarage constructorGarage;

    @Test
    public void givenCarFieldInjectedIntoGarage_whenGarageUsed_thenCarShouldBeAvailable() {
        assertNotNull(fieldGarage.getCar());
    }

    @Test
    public void givenCarMethodInjectedIntoGarage_whenGarageUsed_thenCarShouldBeAvailable() {
        assertNotNull(methodGarage.getCar());
    }

    @Test
    public void givenCarConstructorInjectedIntoGarage_whenGarageUsed_thenCarShouldBeAvailable() {
        assertNotNull(constructorGarage.getCar());
    }
}