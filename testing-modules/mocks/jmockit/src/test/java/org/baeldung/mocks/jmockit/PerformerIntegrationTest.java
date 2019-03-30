package org.baeldung.mocks.jmockit;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.*;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class PerformerIntegrationTest {

    @Injectable
    private Collaborator collaborator;

    @Tested
    private Performer performer;

    @Test
    public void testThePerformMethod(@Mocked Model model) {
    	new Expectations() {{
    		model.getInfo();result = "bar";
    		collaborator.collaborate("bar"); result = true;
    	}};

    	performer.perform(model);

    	new Verifications() {{
    		collaborator.receive(true);
    	}};
    }

}
