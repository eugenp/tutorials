package org.baeldung.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class ListEndpoints extends AbstractEndpoint<List<EndpointDTO>> {
    private List<EndpointDTO> endpointDTOs;

    @Autowired
    public ListEndpoints(List<Endpoint> endpoints) {
        super("listEndpoints");
        this.endpointDTOs = endpoints.stream().map(endpoint -> new EndpointDTO(endpoint.getId(), endpoint.isEnabled(), endpoint.isSensitive())).collect(Collectors.toList());
    }

    public List<EndpointDTO> invoke() {
        return this.endpointDTOs;
    }
}