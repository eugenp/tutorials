package com.baeldung.hibernate.jpabootstrap.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class HibernatePersistenceUnitInfo implements PersistenceUnitInfo {
    
    public static final String JPA_VERSION = "2.1";
    private final String persistenceUnitName;
    private PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    private final List<String> managedClassNames;
    private final List<String> mappingFileNames = new ArrayList<>();
    private final Properties properties;
    private DataSource jtaDataSource;
    private DataSource nonjtaDataSource;
    private final List<ClassTransformer> transformers = new ArrayList<>();
    
    public HibernatePersistenceUnitInfo(String persistenceUnitName, List<String> managedClassNames, Properties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }
    
    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }
 
    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }
 
    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return transactionType;
    }
 
    public HibernatePersistenceUnitInfo  setJtaDataSource(DataSource jtaDataSource) {
        this.jtaDataSource = jtaDataSource;
        this.nonjtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.JTA;
        return this;
    }
 
    @Override
    public DataSource getJtaDataSource() {
        return jtaDataSource;
    }
    
    public HibernatePersistenceUnitInfo setNonJtaDataSource(DataSource nonJtaDataSource) {
        this.nonjtaDataSource = nonJtaDataSource;
        this.jtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
        return this;
    }
     
    @Override
    public DataSource getNonJtaDataSource() {
        return nonjtaDataSource;
    }
 
    @Override
    public List<String> getMappingFileNames() {
        return mappingFileNames;
    }
 
    @Override
    public List<URL> getJarFileUrls() {
        return Collections.emptyList();
    }
 
    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }
 
    @Override
    public List<String> getManagedClassNames() {
        return managedClassNames;
    }
 
    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }
 
    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }
 
    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }
 
    public Properties getProperties() {
        return properties;
    }
 
    @Override
    public String getPersistenceXMLSchemaVersion() {
        return JPA_VERSION;
    }
 
    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
 
    @Override
    public void addTransformer(ClassTransformer transformer) {
        transformers.add(transformer);
    }
    
    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}