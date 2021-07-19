package com.baeldung;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.RegisterExtension;
import com.baeldung.extensions.EmployeeDatabaseSetupExtension;

public class MultipleExtensionsUnitTest {

    @Order(1)
    @RegisterExtension
    static EmployeeDatabaseSetupExtension SECOND_DB =
      new EmployeeDatabaseSetupExtension("jdbc:h2:mem:DbTwo;DB_CLOSE_DELAY=-1", "org.h2.Driver", "sa", "");

    @Order(0)
    @RegisterExtension
    static EmployeeDatabaseSetupExtension FIRST_DB =
      new EmployeeDatabaseSetupExtension("jdbc:h2:mem:DbOne;DB_CLOSE_DELAY=-1", "org.h2.Driver", "sa", "");

    @RegisterExtension
    static EmployeeDatabaseSetupExtension LAST_DB =
      new EmployeeDatabaseSetupExtension("jdbc:h2:mem:DbLast;DB_CLOSE_DELAY=-1", "org.h2.Driver", "sa", "");

    @Test
    public void justDemonstratingTheIdea() {
        // empty test
    }
}
