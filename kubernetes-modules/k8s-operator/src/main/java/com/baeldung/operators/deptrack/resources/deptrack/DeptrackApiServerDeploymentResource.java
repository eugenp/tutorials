package com.baeldung.operators.deptrack.resources.deptrack;

import static com.baeldung.operators.deptrack.resources.deptrack.BuilderHelper.fromPrimary;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.baeldung.operators.deptrack.resources.Constants;

import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.LabelSelectorBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpecBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpecBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ResourceIDMatcherDiscriminator;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import io.javaoperatorsdk.operator.processing.event.ResourceID;

@KubernetesDependent( resourceDiscriminator = DeptrackApiServerDeploymentResource.Discriminator.class)
public class DeptrackApiServerDeploymentResource extends CRUDKubernetesDependentResource<Deployment, DeptrackResource> {

    public static final String COMPONENT = "api-server";

    private Deployment template;
    public DeptrackApiServerDeploymentResource() {
        super(Deployment.class);
        this.template = BuilderHelper.loadTemplate(Deployment.class, "templates/api-server-deployment.yaml");
    }

    @Override
    protected Deployment desired(DeptrackResource primary, Context<DeptrackResource> context) {

        ObjectMeta meta = fromPrimary(primary,COMPONENT)
          .build();

        return new DeploymentBuilder(template)
          .withMetadata(meta)
          .withSpec(buildSpec(primary, meta))
          .build();
    }

    private DeploymentSpec buildSpec(DeptrackResource primary, ObjectMeta primaryMeta) {

        return new DeploymentSpecBuilder()
          .withSelector(buildSelector(primaryMeta.getLabels()))
          .withReplicas(1) // Dependenty track does not support multiple pods (yet)
          .withTemplate(buildPodTemplate(primary,primaryMeta))
          .build();
    }

    private LabelSelector buildSelector(Map<String, String> labels) {
        return new LabelSelectorBuilder()
          .addToMatchLabels(labels)
          .build();
    }

    private PodTemplateSpec buildPodTemplate(DeptrackResource primary, ObjectMeta primaryMeta) {

        return new PodTemplateSpecBuilder()
          .withMetadata(primaryMeta)
          .withSpec(buildPodSpec(primary))
          .build();
    }

    private PodSpec buildPodSpec(DeptrackResource primary) {

        // Check for version override
        String imageVersion = StringUtils.hasText(primary.getSpec().getApiServerVersion())?
          ":" + primary.getSpec().getApiServerVersion().trim():"";

        // Check for image override
        String imageName = StringUtils.hasText(primary.getSpec().getApiServerImage())?
          primary.getSpec().getApiServerImage().trim(): Constants.DEFAULT_API_SERVER_IMAGE;

        //@formatter:off
        return new PodSpecBuilder(template.getSpec().getTemplate().getSpec())
          .editContainer(0) // Assumes we have a single container
            .withImage(imageName + imageVersion)
            .and()
          .build();
        //@formatter:on
    }


    static class Discriminator extends ResourceIDMatcherDiscriminator<Deployment,DeptrackResource> {
        public Discriminator() {
            super(COMPONENT, (p) -> new ResourceID(p.getMetadata()
              .getName() + "-" + COMPONENT, p.getMetadata()
              .getNamespace()));
        }
    }

}
