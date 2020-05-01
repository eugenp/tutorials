package com.baeldung.quarkus.liquibase;

import io.quarkus.agroal.deployment.DataSourceInitializedBuildItem;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.deployment.BeanContainerListenerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import liquibase.exception.LiquibaseException;

public class LiquibaseProcessor {

    private LiquibaseConfig liquibaseConfig;

    @Record(ExecutionTime.STATIC_INIT)
    @BuildStep
    void build(BuildProducer<AdditionalBeanBuildItem> additionalBeanProducer,
               BuildProducer<FeatureBuildItem> featureProducer,
               LiquibaseRecorder recorder,
               BuildProducer<BeanContainerListenerBuildItem> containerListenerProducer,
               DataSourceInitializedBuildItem dataSourceInitializedBuildItem) {

        featureProducer.produce(new FeatureBuildItem("liquibase"));

        AdditionalBeanBuildItem unremovableProducer = AdditionalBeanBuildItem.unremovableOf(LiquibaseProducer.class);
        additionalBeanProducer.produce(unremovableProducer);

        containerListenerProducer.produce(
                new BeanContainerListenerBuildItem(recorder.setLiquibaseConfig(liquibaseConfig)));
    }

    @Record(ExecutionTime.RUNTIME_INIT)
    @BuildStep
    void processMigration(LiquibaseRecorder recorder, BeanContainerBuildItem beanContainer) throws LiquibaseException {
        recorder.migrate(beanContainer.getValue());
    }
}
