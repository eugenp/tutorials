package com.baeldung.easyrules;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;

import com.baeldung.easyrules.domain.Person;

/**
 * Taken from https://github.com/j-easy/easy-rules/wiki/shop
 * @author SinNe002
 *
 */
public class AlcoholShopRulesLauncher {
    public static void main(String[] args) throws FileNotFoundException {
        //create a person instance (fact)
        Person tom = new Person("Tom", 14);
        Facts facts = new Facts();
        facts.put("person", tom);

        
        Rule ageRule = new MVELRule()
                .name("age rule")
                .description("Check if person's age is > 18 and marks the person as adult")
                .priority(1)
                .when("person.age > 18")
                .then("person.setAdult(true);");
        

        Rule alcoholRule = MVELRuleFactory.createRuleFrom(new FileReader("alcohol-rule.yml"));

        // create a rule set
        Rules rules = new Rules();
        rules.register(ageRule);
        rules.register(alcoholRule);

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        System.out.println("Tom: Hi! can I have some Vodka please?");
        rulesEngine.fire(rules, facts);
    }
}
