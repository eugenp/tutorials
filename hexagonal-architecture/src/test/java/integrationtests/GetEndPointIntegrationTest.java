package integrationtests;

import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;

import integrationtests.junitrunner.E2eJunitRunner;

@TargetEnv("application_host.properties")
@RunWith(E2eJunitRunner.class)
public class GetEndPointIntegrationTest {

	@Test
    @JsonTestCase("integration_tests/get/get_person_by_personid_test.json")
    public void test_registerpersonAndThenFetchDetaillsOfNewRunner() throws Exception {
    }
	
}
