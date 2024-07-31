package com.baeldung.operators.deptrack.resources.deptrack;

import static com.baeldung.operators.deptrack.resources.deptrack.BuilderHelper.fromPrimary;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.networking.v1.HTTPIngressRuleValue;
import io.fabric8.kubernetes.api.model.networking.v1.HTTPIngressRuleValueBuilder;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent
public class DeptrackIngressResource  extends CRUDKubernetesDependentResource<Ingress,DeptrackResource> {

    private static final String COMPONENT = "ingress";
    private final Ingress template;

    public DeptrackIngressResource() {
        super(Ingress.class);
        this.template = BuilderHelper.loadTemplate(Ingress.class, "templates/ingress.yaml");
    }

    @Override
    protected Ingress desired(DeptrackResource primary, Context<DeptrackResource> context) {

        ObjectMeta meta = fromPrimary(primary,COMPONENT)
          .build();

        return new IngressBuilder(template)
          .withMetadata(meta)
          .editSpec()
            .editDefaultBackend()
              .editOrNewService()
                .withName(primary.getFrontendServiceName())
              .endService()
            .endDefaultBackend()
          .editFirstRule()
            .withHost(primary.getSpec().getIngressHostname())
            .withHttp(buildHttpRule(primary))
          .endRule()
          .endSpec()
          .build();

    }

    private HTTPIngressRuleValue buildHttpRule(DeptrackResource primary) {

        return new HTTPIngressRuleValueBuilder()
          // Backend route
          .addNewPath()
            .withPath("/api")
            .withPathType("Prefix")
            .withNewBackend()
              .withNewService()
                .withName(primary.getApiServerServiceName())
                .withNewPort()
                  .withName("http")
                .endPort()
              .endService()
            .endBackend()
          .endPath()
          //  Frontend route
          .addNewPath()
            .withPath("/")
              .withPathType("Prefix")
                .withNewBackend()
                  .withNewService()
                    .withName(primary.getFrontendServiceName())
                    .withNewPort()
                      .withName("http")
                    .endPort()
                  .endService()
                .endBackend()
              .endPath()
          .build();
    }
}
