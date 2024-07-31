package com.baeldung.operators.deptrack.controller.deptrack;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.baeldung.operators.deptrack.resources.deptrack.DeptrackApiServerDeploymentResource;
import com.baeldung.operators.deptrack.resources.deptrack.DeptrackApiServerServiceResource;
import com.baeldung.operators.deptrack.resources.deptrack.DeptrackFrontendDeploymentResource;
import com.baeldung.operators.deptrack.resources.deptrack.DeptrackFrontendServiceResource;
import com.baeldung.operators.deptrack.resources.deptrack.DeptrackIngressResource;
import com.baeldung.operators.deptrack.resources.deptrack.DeptrackResource;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerConfiguration(dependents = {
    @Dependent(name = DeptrackApiServerDeploymentResource.COMPONENT, type = DeptrackApiServerDeploymentResource.class),
    @Dependent(name = DeptrackFrontendDeploymentResource.COMPONENT, type = DeptrackFrontendDeploymentResource.class),
    @Dependent(name = DeptrackApiServerServiceResource.COMPONENT, type = DeptrackApiServerServiceResource.class),
    @Dependent(name = DeptrackFrontendServiceResource.COMPONENT, type = DeptrackFrontendServiceResource.class),
    @Dependent(type = DeptrackIngressResource.class )
})
@Slf4j
@RequiredArgsConstructor
@Component
public class DeptrackOperatorReconciler implements Reconciler<DeptrackResource> {

    private final ApplicationContext ctx;


    @PostConstruct
    void onPostConstruct() {
        log.info("Reconciler created");
    }

    @Override
    public UpdateControl<DeptrackResource> reconcile(DeptrackResource resource, Context<DeptrackResource> context) throws Exception {
        return UpdateControl.noUpdate();
    }
}
