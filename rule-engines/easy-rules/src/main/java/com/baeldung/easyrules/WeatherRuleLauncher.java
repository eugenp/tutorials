package com.baeldung.easyrules;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class WeatherRuleLauncher {

    public static void main(String[] args) {
        // define facts
        Facts facts = new Facts();
        facts.put("rain", true);

        // define rules
        WeatherRule weatherRule = new WeatherRule();
        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
