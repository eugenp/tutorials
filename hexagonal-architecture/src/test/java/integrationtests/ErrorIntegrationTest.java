package integrationtests;

import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;

import integrationtests.junitrunner.E2eJunitRunner;


@TargetEnv("application_host.properties")
@RunWith(E2eJunitRunner.class)
public class ErrorIntegrationTest {

    @Test
    @JsonTestCase("integration_tests/error/error_msg_get_person_by_invalid_id.json")
    public void test_getpersonWithInvalidid(){
    }

    @Test
    @JsonTestCase("integration_tests/error/error_msg_bad_url_test.json")
    public void test_badUrl(){
    }

}
