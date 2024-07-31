package com.baeldung.operators.deptrack.resources.deptrack;

import java.util.Map;

import lombok.Data;

@Data
public class DeptrackSpec {

    // Images
    private String apiServerImage = "dependencytrack/apiserver";
    private String apiServerVersion = "";

    private String frontendImage = "dependencytrack/frontend";
    private String frontendVersion = "";

    // PVC settings: NOT IMPLEMENTED
    private String pvcClass = ""; // Use default storage class
    private String pvcSize = "10Gi";


    // Database settings: NOT IMPLEMENTED
    private String dbUrl;
    private String dbDriver = "org.postgresql.Driver";
    private String dbSecret;


    // Ingress settings
    private String ingressHostname;
    private Map<String,String> ingressAnnotations;

}
