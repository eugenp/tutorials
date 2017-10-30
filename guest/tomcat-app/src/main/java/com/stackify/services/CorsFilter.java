package com.stackify.services;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

   @Override
   public void filter(final ContainerRequestContext requestContext,
                      final ContainerResponseContext response) throws IOException {
       response.getHeaders().add("Access-Control-Allow-Origin", "*");
       response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept");
   }
}
