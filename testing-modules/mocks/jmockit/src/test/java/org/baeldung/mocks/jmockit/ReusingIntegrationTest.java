package org.baeldung.mocks.jmockit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class ReusingIntegrationTest {

    @Injectable
    private Collaborator collaborator;
    
    @Mocked
    private Model model;

    @Tested
    private Performer performer;
    
    @Before
    public void setup(){
        new Expectations(){{
           model.getInfo(); result = "foo"; minTimes = 0;
           collaborator.collaborate("foo"); result = true; minTimes = 0; 
        }};
    }

    @Test
    public void testWithSetup() {
        performer.perform(model);
        verifyTrueCalls(1);
    }
    
    protected void verifyTrueCalls(int calls){
        new Verifications(){{
           collaborator.receive(true); times = calls; 
        }};
    }
    
    final class TrueCallsVerification extends Verifications{
        public TrueCallsVerification(int calls){
            collaborator.receive(true); times = calls; 
        }
    }
    
    @Test
    public void testWithFinalClass() {
        performer.perform(model);
        new TrueCallsVerification(1);
    }
}
