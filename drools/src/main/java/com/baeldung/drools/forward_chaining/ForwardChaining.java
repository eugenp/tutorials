package com.baeldung.drools.forward_chaining;

import com.baeldung.drools.config.DroolsBeanFactory;
import com.baeldung.drools.model.Applicant;
import com.baeldung.drools.model.Product;
import com.baeldung.drools.model.SuggestedRole;
import org.kie.api.runtime.KieSession;

public class ForwardChaining {
    public static void main(String[] args) {
        ForwardChaining result = new ForwardChaining();
        result.forwardChaining();
    }

    private void forwardChaining() {
        KieSession ksession = new DroolsBeanFactory().getKieSession();
        try {
            Applicant applicant = new Applicant("Daniel", 38, 1_600_000.0, 11);
            SuggestedRole suggestedRole = new SuggestedRole();

            ksession.setGlobal("suggestedRole", suggestedRole);
            ksession.insert(applicant);

            int fired = ksession.fireAllRules();
            System.out.println("Rules fired: " + fired);
            System.out.println("Suggested role: " + suggestedRole.getRole());
        } finally {
            ksession.dispose();
        }
    }
}
