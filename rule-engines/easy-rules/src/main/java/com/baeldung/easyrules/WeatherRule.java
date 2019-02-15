package com.baeldung.easyrules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

//This example taken from https://github.com/j-easy/easy-rules 
@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class WeatherRule {

    @Condition
    public boolean itRains(@Fact("rain") boolean rain) {
	return rain;
    }
    

    @Action
    public void takeAnUmbrella() {
	System.out.println("It rains, take an umbrella!");
    }
}