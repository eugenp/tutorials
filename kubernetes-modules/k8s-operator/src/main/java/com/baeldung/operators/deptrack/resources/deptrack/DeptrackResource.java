package com.baeldung.operators.deptrack.resources.deptrack;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("com.baeldung")
@Version("v1")
public class DeptrackResource extends CustomResource<DeptrackSpec, DeptrackStatus>  implements Namespaced {

    @JsonIgnore
    public String getFrontendServiceName() {
        return this.getMetadata().getName() + "-" + DeptrackFrontendServiceResource.COMPONENT;
    }

    @JsonIgnore
    public String getApiServerServiceName() {
        return this.getMetadata().getName() + "-" + DeptrackApiServerServiceResource.COMPONENT;
    }
}
