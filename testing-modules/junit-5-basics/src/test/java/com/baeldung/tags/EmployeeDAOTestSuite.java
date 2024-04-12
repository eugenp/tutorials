package com.baeldung.tags;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;

@SelectPackages("com.baeldung.tags")
@IncludeTags("UnitTest")
public class EmployeeDAOTestSuite {
}
