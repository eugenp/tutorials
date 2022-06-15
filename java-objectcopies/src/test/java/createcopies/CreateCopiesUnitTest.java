package createcopies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import computermodel.ComputerDeep;
import computermodel.ComputerShallow;

class CreateCopiesUnitTest {

    @Test
    void whenCreateDeepCopyMethodIsCalled_thenDeepCopyShouldBeCreated() {
        ComputerDeep newComputer = CreateCopies.createDeepCopy();
        Assertions.assertEquals("Intel", newComputer.getCpu().getBrandName());
    }

    @Test
    void whenCreateShallowCopyMethodIsCalled_thenShallowCopyShouldBeCreated() {
        ComputerShallow newComputer = CreateCopies.createShallowCopy();
        Assertions.assertEquals("AMD", newComputer.getCpu().getBrandName());
    }

}
