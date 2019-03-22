package com.baeldung.serverconfig;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("skipAutoConfig")
public class GreetingSkipAutoConfigLiveTest extends GreetingLiveTest {

}
