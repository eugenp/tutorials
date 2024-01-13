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

@KubernetesDependent(resourceDiscriminator = DeptrackFrontendDeploymentResource.Discriminator.class)
public class DeptrackFrontendDeploymentResource extends CRUDKubernetesDependentResource<Deployment, DeptrackResource> {
    public static final String COMPONENT = "frontend";
    private Deployment template;

    public DeptrackFrontendDeploymentResource() {
        super(Deployment.class);
        this.template = BuilderHelper.loadTemplate(Deployment.class, "templates/frontend-deployment.yaml");
    }

    @Override
    protected Deployment desired(DeptrackResource primary, Context<DeptrackResource> context) {

        ObjectMeta meta = fromPrimary(primary,COMPONENT)
          .build();

        return new DeploymentBuilder(template).withMetadata(meta)
          .withSpec(buildSpec(primary, meta))
          .build();
    }

    private DeploymentSpec buildSpec(DeptrackResource primary, ObjectMeta primaryMeta) {

        return new DeploymentSpecBuilder().withSelector(buildSelector(primaryMeta.getLabels()))
          .withReplicas(1) // Dependency track does not support multiple pods (yet)
          .withTemplate(buildPodTemplate(primary, primaryMeta))
          .build();
    }

    private LabelSelector buildSelector(Map<String, String> labels) {
        return new LabelSelectorBuilder().addToMatchLabels(labels)
          .build();
    }

    private PodTemplateSpec buildPodTemplate(DeptrackResource primary, ObjectMeta primaryMeta) {

        return new PodTemplateSpecBuilder().withMetadata(primaryMeta)
          .withSpec(buildPodSpec(primary))
          .build();
    }

    private PodSpec buildPodSpec(DeptrackResource primary) {

        // Check for version override
        String imageVersion = StringUtils.hasText(primary.getSpec()
          .getFrontendVersion()) ? ":" + primary.getSpec()
          .getFrontendVersion()
          .trim() : "";

        // Check for image override
        String imageName = StringUtils.hasText(primary.getSpec()
          .getFrontendImage()) ? primary.getSpec()
          .getFrontendImage()
          .trim() : Constants.DEFAULT_FRONTEND_IMAGE;

        return new PodSpecBuilder(template.getSpec().getTemplate().getSpec())
          .editContainer(0)
            .withImage(imageName + imageVersion)
            .editFirstEnv()
              .withName("API_BASE_URL")
              .withValue("https://" + primary.getSpec().getIngressHostname())
            .endEnv()
          .and()
          .build();
    }

    static class Discriminator extends ResourceIDMatcherDiscriminator<Deployment,DeptrackResource> {
        public Discriminator() {
            super(COMPONENT, (p) -> new ResourceID(p.getMetadata()
              .getName() + "-" + COMPONENT, p.getMetadata()
              .getNamespace()));
        }
    }

}
