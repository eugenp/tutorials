package com.baeldung.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.baeldung.drools.model.Fact;
import com.baeldung.drools.model.Result;

import static junit.framework.TestCase.assertEquals;

@RunWith(value = JUnit4.class)
public class BackwardChainingTest {
    private Result result;
    private KieServices ks;
    private KieContainer kContainer;
    private KieSession ksession;

    @Before
    public void before() {
        result = new Result();
        ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer();
        ksession = kContainer.newKieSession("ksession-backward-chaining");
        ksession.setGlobal("result", result);
    }

    @Test
    public void whenWallOfChinaIsGiven_ThenItBelongsToPlanetEarth() {

        ksession.setGlobal("result", result);
        ksession.insert(new Fact("Asia", "Planet Earth"));
        ksession.insert(new Fact("China", "Asia"));
        ksession.insert(new Fact("Great Wall of China", "China"));

        ksession.fireAllRules();
        
        // Assert Decision one
        assertEquals(result.getValue(), "Decision one taken: Great Wall of China BELONGS TO Planet Earth");
    }

    @Test
    public void whenChinaIsNotGiven_ThenWallOfChinaDoesNotBelongToPlanetEarth() {
        ksession.insert(new Fact("Asia", "Planet Earth"));
        // ksession.insert(new Location("China", "Asia")); // not provided to force Decision two
        ksession.insert(new Fact("Great Wall of China", "China"));
        
        ksession.fireAllRules();
        
        // Assert Decision two
        assertEquals(result.getValue(), "Decision two taken: Great Wall of China DOES NOT BELONG TO Planet Earth");
    }
}
