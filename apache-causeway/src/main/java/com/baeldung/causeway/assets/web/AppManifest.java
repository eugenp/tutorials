package com.baeldung.causeway.assets.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.apache.causeway.applib.CausewayModuleApplibMixins;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.security.simple.CausewayModuleSecuritySimple;
import org.apache.causeway.security.simple.realm.SimpleRealm;
import org.apache.causeway.security.simple.realm.SimpleRealm.Grant;
import org.apache.causeway.viewer.restfulobjects.jaxrsresteasy.CausewayModuleViewerRestfulObjectsJaxrsResteasy;
import org.apache.causeway.viewer.wicket.applib.CausewayModuleViewerWicketApplibMixins;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import com.baeldung.causeway.assets.AssetManagementModule;

@Configuration
@Import({
    CausewayModuleApplibMixins.class,
    CausewayModuleCoreRuntimeServices.class,
    CausewayModuleSecuritySimple.class,
    CausewayModulePersistenceJpaEclipselink.class,
    CausewayModuleViewerRestfulObjectsJaxrsResteasy.class,
    CausewayModuleViewerWicketApplibMixins.class,
    CausewayModuleViewerWicketViewer.class,
    AssetManagementModule.class
})
@PropertySource(CausewayPresets.NoTranslations)
public class AppManifest {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleRealm simpleRealm(final PasswordEncoder passwordEncoder) {
        final String password = passwordEncoder.encode("pass");
        return new SimpleRealm()
            .addRole("admin_role", feature -> Grant.CHANGE)
            .addUser("admin", password, List.of("admin_role"));
    }
}
