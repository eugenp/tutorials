package com.baeldung;


import javax.ws.rs.ApplicationPath;

import org.apache.cayenne.configuration.server.ServerRuntime;
import org.glassfish.jersey.server.ResourceConfig;

import com.nhl.link.rest.runtime.LinkRestBuilder;
import com.nhl.link.rest.runtime.LinkRestRuntime;

@ApplicationPath("/linkrest")
public class LinkRestApplication extends ResourceConfig {

    public LinkRestApplication() {
        ServerRuntime cayenneRuntime = ServerRuntime.builder()
                .addConfig("cayenne-linkrest-project.xml")
                .build();
        LinkRestRuntime lrRuntime = LinkRestBuilder.build(cayenneRuntime);
        super.register(lrRuntime);
        packages("com.baeldung.linkrest.apis");
    }

}
