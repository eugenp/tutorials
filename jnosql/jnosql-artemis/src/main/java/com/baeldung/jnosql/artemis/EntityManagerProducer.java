package com.baeldung.jnosql.artemis;

import org.jnosql.artemis.ConfigurationUnit;
import org.jnosql.diana.api.document.DocumentCollectionManager;
import org.jnosql.diana.api.document.DocumentCollectionManagerFactory;
import org.jnosql.diana.mongodb.document.MongoDBDocumentCollectionManager;
import org.jnosql.diana.mongodb.document.MongoDBDocumentConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class EntityManagerProducer {

    private static final String DATABASE = "todos";

    private MongoDBDocumentConfiguration configuration;
    private DocumentCollectionManagerFactory managerFactory;

    @PostConstruct
    public void init() {
        configuration = new MongoDBDocumentConfiguration();
        managerFactory = configuration.get();
    }

    @Produces
    public DocumentCollectionManager getEntityManager() {
        return managerFactory.get(DATABASE);
    }

    public void close(@Disposes DocumentCollectionManager entityManager) {
        entityManager.close();
    }

}
