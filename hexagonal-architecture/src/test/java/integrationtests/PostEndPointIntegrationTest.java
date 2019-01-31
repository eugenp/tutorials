package integrationtests;

import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;

import integrationtests.junitrunner.E2eJunitRunner;

@TargetEnv("application_host.properties")
@RunWith(E2eJunitRunner.class)
public class PostEndPointIntegrationTest {

	@Test
    @JsonTestCase("integration_tests/post/post_new_person.json")
    public void test_personRegistration() throws Exception {
    }
	
}
