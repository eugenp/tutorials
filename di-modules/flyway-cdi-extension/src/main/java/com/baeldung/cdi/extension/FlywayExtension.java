package com.baeldung.cdi.extension;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.literal.InjectLiteral;
import javax.enterprise.inject.spi.*;
import javax.enterprise.util.AnnotationLiteral;


/**
 * Flyway is now under CDI container like:
 *
 * @ApplicationScoped
 * @FlywayType public class Flyway{
 * @Inject setDataSource(DataSource dataSource){
 * //...
 * }
 * }
 */

public class FlywayExtension implements Extension {

    DataSourceDefinition dataSourceDefinition = null;

    public void registerFlywayType(@Observes BeforeBeanDiscovery bbdEvent) {
        bbdEvent.addAnnotatedType(Flyway.class, Flyway.class.getName());
    }

    public void detectDataSourceDefinition(@Observes @WithAnnotations(DataSourceDefinition.class) ProcessAnnotatedType<?> patEvent) {
        AnnotatedType at = patEvent.getAnnotatedType();
        dataSourceDefinition = at.getAnnotation(DataSourceDefinition.class);
    }

    public void processAnnotatedType(@Observes ProcessAnnotatedType<Flyway> patEvent) {
        patEvent.configureAnnotatedType()
                //Add Scope
                .add(ApplicationScoped.Literal.INSTANCE)
                //Add Qualifier
                .add(new AnnotationLiteral<FlywayType>() {
                })
                //Decorate setDataSource(DataSource dataSource){} with @Inject
                .filterMethods(annotatedMethod -> {
                    return annotatedMethod.getParameters().size() == 1 &&
                            annotatedMethod.getParameters().get(0).getBaseType().equals(javax.sql.DataSource.class);
                })
                .findFirst().get().add(InjectLiteral.INSTANCE);
    }

    void afterBeanDiscovery(@Observes AfterBeanDiscovery abdEvent, BeanManager bm) {
        abdEvent.addBean()
                .types(javax.sql.DataSource.class, DataSource.class)
                .qualifiers(new AnnotationLiteral<Default>() {}, new AnnotationLiteral<Any>() {})
                .scope(ApplicationScoped.class)
                .name(DataSource.class.getName())
                .beanClass(DataSource.class)
                .createWith(creationalContext -> {
                    DataSource instance = new DataSource();
                    instance.setUrl(dataSourceDefinition.url());
                    instance.setDriverClassName(dataSourceDefinition.className());
                    return instance;
                });
    }

    void runFlywayMigration(@Observes AfterDeploymentValidation adv, BeanManager manager) {
        Flyway flyway = manager.createInstance().select(Flyway.class, new AnnotationLiteral<FlywayType>() {}).get();
        flyway.migrate();
    }
}
