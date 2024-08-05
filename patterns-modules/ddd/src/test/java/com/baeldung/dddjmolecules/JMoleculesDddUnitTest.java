package com.baeldung.dddjmolecules;

import org.jmolecules.archunit.JMoleculesDddRules;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(packages = "com.baeldung.dddjmolecules")
class JMoleculesDddUnitTest {

    @ArchTest
    void whenCheckingAllClasses_thenCodeFollowsAllDddPrinciples(JavaClasses classes) {
        JMoleculesDddRules.all()
            .check(classes);
    }
}

