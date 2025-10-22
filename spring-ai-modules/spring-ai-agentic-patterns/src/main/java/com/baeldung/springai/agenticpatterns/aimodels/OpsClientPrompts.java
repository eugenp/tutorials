package com.baeldung.springai.agenticpatterns.aimodels;

public final class OpsClientPrompts {

    private OpsClientPrompts() {
    }

    /**
     * Array of steps to be taken for the dev pipeline
     */
    public static final String[] DEV_PIPELINE_STEPS = {
      // Checkout from VCS
      """
					Checkout the PR from the link.
					If error occurs, return the error,
					   else return the path of the checked-out code""",
      // Build the code and package
      """
					Identify the build tool and build the code of the given path.
					If error occurs, return the error,
					   else return the path of the input""",
      // Containerize and push to docker repo
      """
					On the given path, create the docker container. Then push to our private repo.
					If error occurs, return the error,
					   else return the link of the container and the path to the code""",
      // Deploy to test environment
      """
					Deploy the given docker image to test.
					If error occurs, return the error,
					   else return the path of the input""",
      // Run integration tests
      """
					From the PR code, execute the integration tests against test environment.
					If error occurs, return the error,
					   else return success""" };

    /**
     * Prompt for the deployment of a container to one or many environments
     */
    public static final String NON_PROD_DEPLOYMENT_PROMPT =
      // Prompt For Deployment
      """
        Deploy the given container to the given environment.
        If any prod environment is requested, fail.
        If error occurs, return the error,
           else return success message.""";

    /**
     * Array of steps to be taken for deployment and test execution against this environment
     */
    public static final String[] EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS = {
      // Prompt For Deployment. If successful, pass to the next step the PR link and the environment.
      """
        Deploy the container associated to the given PR, on the given environment.
        If any prod environment is requested, fail.
        If error occurs, return the error,
           else return an array of strings: '{the PR link] on {the environment}'.""",
      // Continue with running the tests from the code base that are related to this env.
      // Which means, run the Functional Tests on 'test' env, the Integration Tests on 'int', etc.
      """
        Execute the tests from the codebase version provided, that are related to the environment provided.
        If error occurs, return the error,
          else return the environment as title and then the test outcome.""" };
}
