package org.baeldung.examples.olingo4.edm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;
import org.springframework.stereotype.Component;

@Component
public class JpaEdmProvider extends CsdlAbstractEdmProvider {

    EntityManagerFactory emf;

    //
    private EdmTypeMapper typeMapper;

    // Service Namespace
    public static final String NAMESPACE = "OData.Demo";

    // EDM Container
    public static final String CONTAINER_NAME = "Cars";
    public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

    public JpaEdmProvider(EntityManagerFactory emf, EdmTypeMapper mapper) {
        this.emf = emf;
        this.typeMapper = mapper;
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider#getEntitySet(org.apache.olingo.commons.api.edm.FullQualifiedName, java.lang.String)
     */
    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {

        if (entityContainer.equals(CONTAINER)) {

            EntityType<?> e = emf.getMetamodel()
                .getEntities()
                .stream()
                .filter((ent) -> (ent.getName() + "s")
                    .equals(entitySetName))
                .findFirst()
                .orElse(null);

            if (e != null) {
                CsdlEntitySet entitySet = new CsdlEntitySet();
                entitySet
                  .setName(entitySetName)
                  .setType(new FullQualifiedName(NAMESPACE, e.getName()));
                return entitySet;
            }
        }

        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider#getEntityContainerInfo(org.apache.olingo.commons.api.edm.FullQualifiedName)
     */
    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {

        // This method is invoked when displaying the Service Document at e.g. http://localhost:8080/DemoService/DemoService.svc
        if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
            CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
            entityContainerInfo.setContainerName(CONTAINER);
            return entityContainerInfo;
        }

        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider#getSchemas()
     */
    @Override
    public List<CsdlSchema> getSchemas() throws ODataException {
        // create Schema
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // add EntityTypes
        List<CsdlEntityType> entityTypes = emf.getMetamodel()
            .getEntities()
            .stream()
            .map((e) -> {
                try {
                    return getEntityType(new FullQualifiedName(NAMESPACE, e.getName()));
                } catch (ODataException oe) {
                    throw new RuntimeException(oe);
                }
            })
            .collect(Collectors.toList());

        schema.setEntityTypes(entityTypes);

        // add EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // finally
        List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
        schemas.add(schema);

        return schemas;
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider#getEntityContainer()
     */
    @Override
    public CsdlEntityContainer getEntityContainer() throws ODataException {
        
        
        // add EntityTypes
        List<CsdlEntitySet> entitySets = emf.getMetamodel()
            .getEntities()
            .stream()
            .map((e) -> {
                try {
                    // Here we use a simple mapping strategy to map entity types to entity set names:
                    return getEntitySet(CONTAINER, e.getName() + "s");
                } catch (ODataException oe) {
                    throw new RuntimeException(oe);
                }
            })
            .collect(Collectors.toList());
        
        // create EntityContainer
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {

        Metamodel mm = emf.getMetamodel();

        CsdlEntityType result = null;

        result = mm.getEntities()
            .stream()
            .filter(et -> entityTypeName.equals(new FullQualifiedName(NAMESPACE, et.getName())))
            .map(et -> buildODataType(et))
            .findFirst()
            .orElse(null);

        return result;
        
    }

    /**
     * Maps a JPA type to its OData counterpart.
     * @param et
     * @return
     */
    protected CsdlEntityType buildODataType(EntityType<?> et) {

        CsdlEntityType result = new CsdlEntityType();
        result.setName(et.getName());

        // Process simple properties
        List<CsdlProperty> properties = et.getDeclaredSingularAttributes()
            .stream()
            .filter(attr -> attr.getPersistentAttributeType() == PersistentAttributeType.BASIC)
            .map(attr -> buildBasicAttribute(et, attr))
            .collect(Collectors.toList());

        result.setProperties(properties);

        // Process Ids
        List<CsdlPropertyRef> ids = et.getDeclaredSingularAttributes()
            .stream()
            .filter(attr -> attr.getPersistentAttributeType() == PersistentAttributeType.BASIC && attr.isId())
            .map(attr -> buildRefAttribute(et, attr))
            .collect(Collectors.toList());

        result.setKey(ids);

        // Process 1:N navs
        List<CsdlNavigationProperty> navs = et.getDeclaredPluralAttributes()
            .stream()
            .map(attr -> buildNavAttribute(et, attr))
            .collect(Collectors.toList());
        result.setNavigationProperties(navs);
        
        // Process N:1 navs
        List<CsdlNavigationProperty> navs2 = et.getDeclaredSingularAttributes()
            .stream()
            .filter(attr -> attr.getPersistentAttributeType() == PersistentAttributeType.MANY_TO_ONE)
            .map(attr -> buildNavAttribute(et, attr))
            .collect(Collectors.toList());

        result.getNavigationProperties().addAll(navs2);
        

        return result;
    }

    private CsdlProperty buildBasicAttribute(EntityType<?> et, SingularAttribute<?, ?> attr) {

        CsdlProperty p = new CsdlProperty().setName(attr.getName())
            .setType(typeMapper.java2edm(attr.getJavaType())
                .getFullQualifiedName())
            .setNullable(et.getDeclaredSingularAttribute(attr.getName())
                .isOptional());

        return p;
    }

    private CsdlPropertyRef buildRefAttribute(EntityType<?> et, SingularAttribute<?, ?> attr) {

        CsdlPropertyRef p = new CsdlPropertyRef().setName(attr.getName());

        return p;
    }

    // Build NavProperty for 1:N or M:N associations
    private CsdlNavigationProperty buildNavAttribute(EntityType<?> et, PluralAttribute<?, ?, ?> attr) {

        CsdlNavigationProperty p = new CsdlNavigationProperty().setName(attr.getName())
            .setType(new FullQualifiedName(NAMESPACE, attr.getBindableJavaType().getSimpleName()))
            .setCollection(true)
            .setNullable(false); 

        return p;
    }

    // Build NavProperty for N:1 associations
    private CsdlNavigationProperty buildNavAttribute(EntityType<?> et, SingularAttribute<?, ?> attr) {

        CsdlNavigationProperty p = new CsdlNavigationProperty().setName(attr.getName())
            .setType(new FullQualifiedName(NAMESPACE, attr.getBindableJavaType().getSimpleName()))
            .setCollection(false)
            .setNullable(attr.isOptional());

        return p;
    }
    
}
