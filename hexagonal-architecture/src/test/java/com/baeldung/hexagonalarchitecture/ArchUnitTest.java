package com.baeldung.hexagonalarchitecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;

@AnalyzeClasses(importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

    private JavaClasses allClasses = new ClassFileImporter().importPackagesOf(Application.class);

    @Test
    void noClassFromDomainLayerShouldDependOnOuterLayer() {
        noClasses().that()
            .resideInAPackage("..domain..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..application..")
            .check(allClasses);
    }

    @Test
    void noClassesFromRestPackageShouldDependOnOtherAdapters() {
        noClasses().that()
            .resideInAPackage("..rest..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..persistence..", "..notifier..")
            .check(allClasses);
    }

    @Test
    void noClassesFromPersistencePackageShouldDependOnOtherAdapters() {
        noClasses().that()
            .resideInAPackage("..persistence..")
            .should()
            .onlyHaveDependentClassesThat()
            .resideInAnyPackage("..rest..", "..notifier..")
            .check(allClasses);
    }

    @Test
    void noClassesFromNotifierPackageShouldDependOnOtherAdapters() {
        noClasses().that()
            .resideInAPackage("..notifier..")
            .should()
            .onlyHaveDependentClassesThat()
            .resideInAnyPackage("..persistence..", "..rest..")
            .check(allClasses);
    }
}
