package myproject;

import static com.pulumi.test.PulumiTest.extractValue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.pulumi.aws.ec2.Instance;
import com.pulumi.aws.ec2.Vpc;
import com.pulumi.test.Mocks;
import com.pulumi.test.PulumiTest;

class WebserverUnitTest {

    @AfterAll
    static void cleanup() {
        PulumiTest.cleanup();
    }

    @TestFactory
    Stream<DynamicTest> givenWebServerInfrastructure() {
        var result = PulumiTest.withMocks(new MyMocks())
          .runTest(WebserverInfra::stack);

        var instance = result.resources()
          .stream()
          .filter(r -> r instanceof Instance)
          .map(r -> (Instance) r)
          .findFirst();

        assertThat(instance).isPresent();

        return Stream.of(dynamicTest("instance must use user data", () -> {
              var userData = instance.map(Instance::userData);
              assertThat(userData).isPresent()
                .isNotEmpty();
          }),

          dynamicTest("VPC should be created with the correct CIDR block", () -> {
              result.resources()
                .stream()
                .filter(r -> r instanceof Vpc)
                .map(r -> (Vpc) r)
                .forEach(vpc -> {
                    var cidrBlock = extractValue(vpc.cidrBlock());
                    assertThat(cidrBlock).isEqualTo("10.0.0.0/16");
                });
          }),

          dynamicTest("instance must have a public IP", () -> {
              var publicIp = instance.map(Instance::publicIp);
              assertThat(publicIp).isPresent()
                .isNotEmpty();
          }));
    }

    // Mock the engine state for Pulumi resources
    public static class MyMocks implements Mocks {

        @Override
        public CompletableFuture<ResourceResult> newResourceAsync(ResourceArgs args) {
            assert args.inputs != null;
            return CompletableFuture.completedFuture(ResourceResult.of(Optional.of(args.name + "_id"), args.inputs));
        }
    }
}