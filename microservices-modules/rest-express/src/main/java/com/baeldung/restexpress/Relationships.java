package com.baeldung.restexpress;

import com.baeldung.restexpress.objectid.SampleOidEntity;
import com.baeldung.restexpress.uuid.SampleUuidEntity;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;
import org.restexpress.RestExpress;
import org.restexpress.common.exception.ConfigurationException;

import java.util.Map;

public abstract class Relationships {
    private static Map<String, String> ROUTES;

    public static void define(RestExpress server) {
        ROUTES = server.getRouteUrlsByName();

        HyperExpress.relationships()
          .forCollectionOf(SampleUuidEntity.class)
          .rel(RelTypes.SELF, href(Constants.Routes.SAMPLE_UUID_COLLECTION))
          .withQuery("limit={limit}")
          .withQuery("offset={offset}")
          .rel(RelTypes.NEXT, href(Constants.Routes.SAMPLE_UUID_COLLECTION) + "?offset={nextOffset}")
          .withQuery("limit={limit}")
          .optional()
          .rel(RelTypes.PREV, href(Constants.Routes.SAMPLE_UUID_COLLECTION) + "?offset={prevOffset}")
          .withQuery("limit={limit}")
          .optional()

          .forClass(SampleUuidEntity.class)
          .rel(RelTypes.SELF, href(Constants.Routes.SINGLE_UUID_SAMPLE))
          .rel(RelTypes.UP, href(Constants.Routes.SAMPLE_UUID_COLLECTION))

          .forCollectionOf(SampleOidEntity.class)
          .rel(RelTypes.SELF, href(Constants.Routes.SAMPLE_OID_COLLECTION))
          .withQuery("limit={limit}")
          .withQuery("offset={offset}")
          .rel(RelTypes.NEXT, href(Constants.Routes.SAMPLE_OID_COLLECTION) + "?offset={nextOffset}")
          .withQuery("limit={limit}")
          .optional()
          .rel(RelTypes.PREV, href(Constants.Routes.SAMPLE_OID_COLLECTION) + "?offset={prevOffset}")
          .withQuery("limit={limit}")
          .optional()

          .forClass(SampleOidEntity.class)
          .rel(RelTypes.SELF, href(Constants.Routes.SINGLE_OID_SAMPLE))
          .rel(RelTypes.UP, href(Constants.Routes.SAMPLE_OID_COLLECTION));
    }

    private static String href(String name) {
        String href = ROUTES.get(name);
        if (href == null) throw new ConfigurationException("Route name not found: " + name);
        return href;
    }
}
