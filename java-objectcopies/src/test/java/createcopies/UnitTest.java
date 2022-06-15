package createcopies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import computermodel.ComputerDeep;
import computermodel.ComputerShallow;

class UnitTest {

    @Test
    void givenAComputerObject_whenCreateDeepCopyMethodIsCalled_thenDeepCopyShouldBeCreated() {
        ComputerDeep newComputer = CreateCopies.createDeepCopy();
        Assertions.assertEquals("Intel", newComputer.getCpu().getBrandName());
    }

    @Test
    void givenAComputerObject_whenCreateShallowCopyMethodIsCalled_thenShallowCopyShouldBeCreated() {
        ComputerShallow newComputer = CreateCopies.createShallowCopy();
        Assertions.assertEquals("AMD", newComputer.getCpu().getBrandName());
    }

}
