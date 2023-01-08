package com.baeldung.restexpress;

public class Constants {
    /**
     * These define the URL parmaeters used in the route definition strings (e.g. '{userId}').
     */
    public class Url {
        //TODO: Your URL parameter names here...
        public static final String SAMPLE_ID = "uuid";
    }

    /**
     * These define the route names used in naming each route definitions.  These names are used
     * to retrieve URL patterns within the controllers by name to create links in responses.
     */
    public class Routes {
        //TODO: Your Route names here...
        public static final String SINGLE_UUID_SAMPLE = "sample.single.route.uuid";
        public static final String SAMPLE_UUID_COLLECTION = "sample.collection.route.uuid";
        public static final String SINGLE_OID_SAMPLE = "sample.single.route.oid";
        public static final String SAMPLE_OID_COLLECTION = "sample.collection.route.oid";
    }
}
