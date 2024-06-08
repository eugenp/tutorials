package com.gateway.web.rest;

import com.gateway.security.AuthoritiesConstants;
import com.gateway.web.rest.vm.RouteVM;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/api/gateway")
public class GatewayResource {

    private final RouteLocator routeLocator;

    private final DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    public GatewayResource(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
    }

    /**
     * {@code GET  /routes} : get the active routes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of routes.
     */
    @GetMapping("/routes")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<RouteVM>> activeRoutes() {
        Flux<Route> routes = routeLocator.getRoutes();
        List<RouteVM> routeVMs = new ArrayList<>();
        routes.subscribe(route -> {
            RouteVM routeVM = new RouteVM();
            // Manipulate strings to make Gateway routes look like Zuul's
            String predicate = route.getPredicate().toString();
            String path = predicate.substring(predicate.indexOf("[") + 1, predicate.indexOf("]"));
            routeVM.setPath(path);
            String serviceId = route.getId().substring(route.getId().indexOf("_") + 1).toLowerCase();
            routeVM.setServiceId(serviceId);
            // Exclude gateway app from routes
            if (!serviceId.equalsIgnoreCase(appName)) {
                routeVM.setServiceInstances(discoveryClient.getInstances(serviceId));
                routeVMs.add(routeVM);
            }
        });
        return ResponseEntity.ok(routeVMs);
    }
}
