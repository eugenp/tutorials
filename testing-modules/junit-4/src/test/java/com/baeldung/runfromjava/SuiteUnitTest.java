package com.baeldung.runfromjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.baeldung.junit.RegistrationUnitTest;
import com.baeldung.junit.SignInUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ RegistrationUnitTest.class, SignInUnitTest.class })
public class SuiteUnitTest {

}
