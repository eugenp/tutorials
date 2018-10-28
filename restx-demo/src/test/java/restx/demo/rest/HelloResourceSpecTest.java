package restx.demo.rest;

import restx.demo.AppServer;
import org.junit.runner.RunWith;
import restx.tests.RestxSpecTestsRunner;
import restx.tests.FindSpecsIn;

@RunWith(RestxSpecTestsRunner.class)
@FindSpecsIn("specs/hello")
public class HelloResourceSpecTest {

    /**
     * Useless, thanks to both @RunWith(RestxSpecTestsRunner.class) & @FindSpecsIn()
     *
     * @Rule
     * public RestxSpecRule rule = new RestxSpecRule();
     *
     * @Test
     * public void test_spec() throws Exception {
     *     rule.runTest(specTestPath);
     * }
     */
}
