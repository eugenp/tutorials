package com.baeldung.causeway.assets.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.applib.services.wrapper.DisabledException;
import org.apache.causeway.applib.services.wrapper.InvalidException;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.security.bypass.CausewayModuleSecurityBypass;
import org.apache.causeway.testing.integtestsupport.applib.CausewayIntegrationTestAbstract;
import org.apache.causeway.testing.integtestsupport.applib.validate.DomainModelValidator;

import com.baeldung.causeway.assets.AssetManagementModule;

@SpringBootTest(classes = AssetIntegTest.TestApp.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "causeway.persistence.schema.auto-create-schemas=assets")
@Transactional
class AssetIntegTest extends CausewayIntegrationTestAbstract {

    @Inject
    private Assets assets;

    @Inject
    private ServiceRegistry serviceRegistry;

    @Test
    void whenUsingDomainActionsThenCausewayEnforcesBusinessRules() {
        final Asset asset = wrap(assets).create(AssetType.LAPTOP, "LT-800");

        assertThrows(InvalidException.class, () -> wrap(asset).assignTo(" "));

        wrap(asset).assignTo("Margaret Hamilton");

        assertEquals(AssetStatus.ASSIGNED, asset.getStatus());
        assertThrows(DisabledException.class, () -> wrap(asset).retire());

        wrap(asset).returnToInventory();
        wrap(asset).retire();

        assertEquals(AssetStatus.RETIRED, asset.getStatus());
    }

    @Test
    void whenValidatingDomainModelThenItIsValid() {
        new DomainModelValidator(serviceRegistry).assertValid();
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @Import({
        CausewayModuleCoreRuntimeServices.class,
        CausewayModuleSecurityBypass.class,
        CausewayModulePersistenceJpaEclipselink.class,
        AssetManagementModule.class
    })
    @PropertySources({
        @PropertySource(CausewayPresets.H2InMemory_withUniqueSchema),
        @PropertySource(CausewayPresets.NoTranslations)
    })
    static class TestApp {
    }
}
