package com.baeldung.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  AdditionTest.class,
  SubstractionTest.class})
public class SuiteTest {
}
