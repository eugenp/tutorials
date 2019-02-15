package com.baeldung.easyrules;


import org.jeasy.rules.api.Rule;
import org.jeasy.rules.mvel.MVELRule;

public class AgeRule {
    // create rules

    public static Rule setupAgeRule() {
	return new MVELRule().name("age rule")
		.description("Check if person's age is > 18 and marks the person as adult").priority(1)
		.when("person.age > 18").then("person.setAdult(true);");
    }

}
