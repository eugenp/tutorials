package com.baeldung.hexarchitecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependencyRuleTests {

    @Test
    void domainLayerDoesNotDependOnApplicationLayer() {
        ArchRule archRule = noClasses()
                .that()
                .resideInAPackage("com.baeldung.hexarchitecture.domain")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("com.baeldung.hexarchitecture.application");

        archRule.check(new ClassFileImporter().importPackages("com.baeldung.hexarchitecture"));
    }
}
