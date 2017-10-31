package com.baeldung;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.baeldung.model.Beatle;

public class BackwardChainingBeatles {
    public static void main(String[] args) {
        
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-backward-chaining");
            // drools session base on the xml configuration (<strong>kmodule.xml</strong>)

            // graph population
            kSession.insert(new Beatle("Starr", "drums"));
            kSession.insert(new Beatle("McCartney", "bass"));
            kSession.insert(new Beatle("Lennon", "guitar"));
            kSession.insert(new Beatle("Harrison", "guitar"));

            kSession.insert("Ringo"); // invoke the rule that calls the query implentation of backward chaining
            kSession.fireAllRules(); // invoke all the rules
        
    }

}