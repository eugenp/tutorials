package com.baeldung.oauth2.authorization.server.api;

import com.baeldung.oauth2.authorization.server.PEMKeyUtils;
import com.nimbusds.jose.jwk.JWK;
import org.eclipse.microprofile.config.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path("jwk")
@ApplicationScoped
public class JWKEndpoint {

    @Inject
    private Config config;

    @GET
    public Response getKey(@QueryParam("format") String format) throws Exception {
        if (format != null && !Arrays.asList("jwk", "pem").contains(format)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Public Key Format should be : jwk or pem").build();
        }
        String verificationkey = config.getValue("verificationkey", String.class);
        String pemEncodedRSAPublicKey = PEMKeyUtils.readKeyAsString(verificationkey);
        if (format == null || format.equals("jwk")) {
            JWK jwk = JWK.parseFromPEMEncodedObjects(pemEncodedRSAPublicKey);
            return Response.ok(jwk.toJSONString()).type(MediaType.APPLICATION_JSON).build();
        } else if (format.equals("pem")) {
            return Response.ok(pemEncodedRSAPublicKey).build();
        }
        return null;
    }
}
