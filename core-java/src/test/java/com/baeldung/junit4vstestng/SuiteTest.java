package com.baeldung.junit4vstestng;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ RegistrationTest.class, SignInTest.class })
public class SuiteTest {

}
