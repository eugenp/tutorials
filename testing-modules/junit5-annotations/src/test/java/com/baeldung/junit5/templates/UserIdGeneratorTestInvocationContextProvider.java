package com.baeldung.junit5.templates;

import org.junit.jupiter.api.extension.*;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class UserIdGeneratorTestInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        boolean featureDisabled = false;
        boolean featureEnabled = true;
        return Stream.of(
            featureDisabledContext(
                new UserIdGeneratorTestCase(
                    "Given feature switch disabled When user name is John Smith Then generated userid is JSmith",
                    featureDisabled, "John", "Smith", "JSmith")),
            featureEnabledContext(
                new UserIdGeneratorTestCase(
                    "Given feature switch enabled When user name is John Smith Then generated userid is baelJSmith",
                    featureEnabled, "John", "Smith", "baelJSmith"))
        );
    }

    private TestTemplateInvocationContext featureDisabledContext(UserIdGeneratorTestCase userIdGeneratorTestCase) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return userIdGeneratorTestCase.getDisplayName();
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return asList(
                    new GenericTypedParameterResolver(userIdGeneratorTestCase), 
                    new BeforeTestExecutionCallback() {
                        @Override
                        public void beforeTestExecution(ExtensionContext extensionContext) {
                            System.out.println("BeforeTestExecutionCallback:Disabled context");
                        }
                    }, 
                    new AfterTestExecutionCallback() {
                        @Override
                        public void afterTestExecution(ExtensionContext extensionContext) {
                            System.out.println("AfterTestExecutionCallback:Disabled context");
                        }
                });
            }
        };
    }

    private TestTemplateInvocationContext featureEnabledContext(UserIdGeneratorTestCase userIdGeneratorTestCase) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return userIdGeneratorTestCase.getDisplayName();
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return asList(
                    new GenericTypedParameterResolver(userIdGeneratorTestCase), 
                    new DisabledOnQAEnvironmentExtension(), 
                    new BeforeEachCallback() {
                        @Override
                        public void beforeEach(ExtensionContext extensionContext) {
                            System.out.println("BeforeEachCallback:Enabled context");
                        }
                    }, 
                    new AfterEachCallback() {
                        @Override
                        public void afterEach(ExtensionContext extensionContext) {
                            System.out.println("AfterEachCallback:Enabled context");
                        }
                    });
            }
        };
    }
}
