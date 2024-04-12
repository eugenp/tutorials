package com.baeldung.archunit.smurfs;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SmurfsArchManualTest {
    
    @Test
    public void givenPresentationLayerClasses_thenWrongCheckFails() {        
        JavaClasses jc = new ClassFileImporter().importPackages("com.baeldung.archunit.smurfs");
        
        ArchRule r1 = classes()
          .that()
          .resideInAPackage("..presentation..")
          .should().onlyDependOnClassesThat()
          .resideInAPackage("..service..");
    
        assertThrows(AssertionError.class, ()-> r1.check(jc)) ;
    }
    

    @Test
    public void givenPresentationLayerClasses_thenCheckWithFrameworkDependenciesSuccess() {        
        JavaClasses jc = new ClassFileImporter().importPackages("com.baeldung.archunit.smurfs");
        
        ArchRule r1 = classes()
          .that()
          .resideInAPackage("..presentation..")
          .should().onlyDependOnClassesThat()
          .resideInAnyPackage("..service..", "java..", "javax..", "org.springframework..");

        r1.check(jc);
    }

    @Test
    public void givenPresentationLayerClasses_thenNoPersistenceLayerAccess() {        
        JavaClasses jc = new ClassFileImporter().importPackages("com.baeldung.archunit.smurfs");
        
        ArchRule r1 = noClasses()
          .that()
          .resideInAPackage("..presentation..")
          .should().dependOnClassesThat()
          .resideInAPackage("..persistence..");
        
        r1.check(jc);
    }
    
    @Test
    public void givenApplicationClasses_thenNoLayerViolationsShouldExist() {
        
        JavaClasses jc = new ClassFileImporter().importPackages("com.baeldung.archunit.smurfs");
        
        LayeredArchitecture arch = layeredArchitecture()
           // Define layers
          .layer("Presentation").definedBy("..presentation..")
          .layer("Service").definedBy("..service..")
          .layer("Persistence").definedBy("..persistence..")
          // Add constraints
          .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
          .whereLayer("Service").mayOnlyBeAccessedByLayers("Presentation")
          .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");
        
        arch.check(jc);
    }
}
