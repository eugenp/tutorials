package com.baeldung.categories;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(UnitTest.class)
@SuiteClasses(EmployeeDAOCategoryIntegrationTest.class)
public class EmployeeDAOUnitTestSuite {
}
