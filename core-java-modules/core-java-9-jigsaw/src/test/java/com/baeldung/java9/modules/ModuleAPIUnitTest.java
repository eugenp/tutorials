package com.baeldung.java9.modules;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.*;
import java.sql.Date;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class ModuleAPIUnitTest {

    public static final String JAVA_BASE_MODULE_NAME = "java.base";

    private Module javaBaseModule;
    private Module javaSqlModule;
    private Module module;

    @Before
    public void setUp() {
        Class<HashMap> hashMapClass = HashMap.class;
        javaBaseModule = hashMapClass.getModule();

        Class<Date> dateClass = Date.class;
        javaSqlModule = dateClass.getModule();

        Class<Person> personClass = Person.class;
        module = personClass.getModule();
    }

    @Test
    public void whenCheckingIfNamed_thenModuleIsNamed() {
        assertThat(javaBaseModule.isNamed(), is(true));
        assertThat(javaBaseModule.getName(), is(JAVA_BASE_MODULE_NAME));
    }

    @Test
    public void whenCheckingIfNamed_thenModuleIsUnnamed() {
        assertThat(module.isNamed(), is(false));
        assertThat(module.getName(), is(nullValue()));
    }

    @Test
    public void whenExtractingPackagesContainedInAModule_thenModuleContainsOnlyFewOfThem() {
        assertTrue(javaBaseModule.getPackages().contains("java.lang.annotation"));
        assertFalse(javaBaseModule.getPackages().contains("java.sql"));
    }

    @Test
    public void whenRetrievingClassLoader_thenClassLoaderIsReturned() {
        assertThat(
          module.getClassLoader().getClass().getName(),
          is("jdk.internal.loader.ClassLoaders$AppClassLoader")
        );
    }

    @Test
    public void whenGettingAnnotationsPresentOnAModule_thenNoAnnotationsArePresent() {
        assertThat(javaBaseModule.getAnnotations().length, is(0));
    }

    @Test
    public void whenGettingLayerOfAModule_thenModuleLayerInformationAreAvailable() {
        ModuleLayer javaBaseModuleLayer = javaBaseModule.getLayer();

        assertTrue(javaBaseModuleLayer.configuration().findModule(JAVA_BASE_MODULE_NAME).isPresent());
        assertTrue(javaBaseModuleLayer.parents().get(0).configuration().parents().isEmpty());
    }

    @Test
    public void whenRetrievingModuleDescriptor_thenTypeOfModuleIsInferred() {
        ModuleDescriptor javaBaseModuleDescriptor = javaBaseModule.getDescriptor();
        ModuleDescriptor javaSqlModuleDescriptor = javaSqlModule.getDescriptor();

        assertFalse(javaBaseModuleDescriptor.isAutomatic());
        assertFalse(javaBaseModuleDescriptor.isOpen());
        assertFalse(javaSqlModuleDescriptor.isAutomatic());
        assertFalse(javaSqlModuleDescriptor.isOpen());
    }

    @Test
    public void givenModuleName_whenBuildingModuleDescriptor_thenBuilt() {
        Builder moduleBuilder = ModuleDescriptor.newModule("baeldung.base");

        ModuleDescriptor moduleDescriptor = moduleBuilder.build();

        assertThat(moduleDescriptor.name(), is("baeldung.base"));
    }

    @Test
    public void givenModules_whenAccessingModuleDescriptorRequires_thenRequiresAreReturned() {
        Set<Requires> javaBaseRequires = javaBaseModule.getDescriptor().requires();
        Set<Requires> javaSqlRequires = javaSqlModule.getDescriptor().requires();

        Set<String> javaSqlRequiresNames = javaSqlRequires.stream()
          .map(Requires::name)
          .collect(Collectors.toSet());

        assertThat(javaBaseRequires, empty());
        assertThat(javaSqlRequiresNames, hasItems("java.base", "java.xml", "java.logging"));
    }

    @Test
    public void givenModules_whenAccessingModuleDescriptorProvides_thenProvidesAreReturned() {
        Set<Provides> javaBaseProvides = javaBaseModule.getDescriptor().provides();
        Set<Provides> javaSqlProvides = javaSqlModule.getDescriptor().provides();

        Set<String> javaBaseProvidesService = javaBaseProvides.stream()
                .map(Provides::service)
                .collect(Collectors.toSet());

        assertThat(javaBaseProvidesService, contains("java.nio.file.spi.FileSystemProvider"));
        assertThat(javaSqlProvides, empty());
    }

    @Test
    public void givenModules_whenAccessingModuleDescriptorExports_thenExportsAreReturned() {
        Set<Exports> javaSqlExports = javaSqlModule.getDescriptor().exports();

        Set<String> javaSqlExportsSource = javaSqlExports.stream()
          .map(Exports::source)
          .collect(Collectors.toSet());

        assertThat(javaSqlExportsSource, hasItems("java.sql",  "javax.sql"));
    }

    @Test
    public void givenModules_whenAccessingModuleDescriptorUses_thenUsesAreReturned() {
        Set<String> javaBaseUses = javaBaseModule.getDescriptor().uses();
        Set<String> javaSqlUses = javaSqlModule.getDescriptor().uses();

        assertThat(javaSqlUses, contains("java.sql.Driver"));
    }

    @Test
    public void givenModules_whenAccessingModuleDescriptorOpen_thenOpenAreReturned() {
        Set<Opens> javaBaseUses = javaBaseModule.getDescriptor().opens();
        Set<Opens> javaSqlUses = javaSqlModule.getDescriptor().opens();

        assertThat(javaBaseUses, empty());
        assertThat(javaSqlUses, empty());
    }

    @Test
    public void whenAddingReadsToAModule_thenModuleCanReadNewModule() {
        Module updatedModule = module.addReads(javaSqlModule);

        assertTrue(updatedModule.canRead(javaSqlModule));
    }

    @Test
    public void whenExportingPackage_thenPackageIsExported() {
        Module updatedModule = module.addExports("com.baeldung.java9.modules", javaSqlModule);

        assertTrue(updatedModule.isExported("com.baeldung.java9.modules"));
    }

    @Test
    public void whenOpeningAModulePackage_thenPackagedIsOpened() {
        Module updatedModule = module.addOpens("com.baeldung.java9.modules", javaSqlModule);

        assertTrue(updatedModule.isOpen("com.baeldung.java9.modules", javaSqlModule));
    }

    @Test
    public void whenAddingUsesToModule_thenUsesIsAdded() {
        Module updatedModule = module.addUses(Driver.class);

        assertTrue(updatedModule.canUse(Driver.class));
    }

    private class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
