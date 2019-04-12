/**
 * 
 */
package org.baeldung.examples.olingo4.processor;

import java.io.InputStream;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataLibraryException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.EntitySerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.baeldung.examples.olingo4.repository.EdmEntityRepository;
import org.baeldung.examples.olingo4.repository.RepositoryRegistry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * JpaEntityProcessor adapter. 
 * <p>This implementation is heavily based on the Tutorial available
 * at Olingo's site. It is meant to be an starting point for an actual implementation.</p>
 * <p>Please note that many features from a full-fledged are missing
 * @author Philippe
 *
 */
@Component
public class JpaEntityProcessor implements EntityProcessor {
    
    private EntityManagerFactory emf;
    private OData odata;
    private ServiceMetadata serviceMetadata;
    private RepositoryRegistry registry;
    private JpaEntityMapper entityMapper;

    public JpaEntityProcessor(EntityManagerFactory emf, RepositoryRegistry registry, JpaEntityMapper entityMapper) {
        this.emf = emf;
        this.registry = registry;
        this.entityMapper = entityMapper;
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.server.api.processor.Processor#init(org.apache.olingo.server.api.OData, org.apache.olingo.server.api.ServiceMetadata)
     */
    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
        
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.server.api.processor.EntityProcessor#readEntity(org.apache.olingo.server.api.ODataRequest, org.apache.olingo.server.api.ODataResponse, org.apache.olingo.server.api.uri.UriInfo, org.apache.olingo.commons.api.format.ContentType)
     */
    @Override
    public void readEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {

        // First, we have to figure out which entity is requested
        List<UriResource> resourceParts = uriInfo.getUriResourceParts();
        InputStream entityStream;
        
        UriResourceEntitySet rootResourceEntitySet = (UriResourceEntitySet) resourceParts.get(0);
        EdmEntitySet rootEntitySet = rootResourceEntitySet.getEntitySet();        
        List<UriParameter> rootPredicates = rootResourceEntitySet.getKeyPredicates();
        EdmEntityType rootEntityType = rootEntitySet.getEntityType();

        
        if ( resourceParts.size() == 1 ) {
            entityStream = readEntity(rootEntitySet,rootPredicates,responseFormat);
        }
        else if ( resourceParts.size() == 2 ) {
            UriResource part = resourceParts.get(1);
            if ( !(part instanceof UriResourceNavigation)) {
                throw new ODataRuntimeException("[E103] part type not supported: class=" + part.getClass().getName());
            }

            UriResourceNavigation navSegment = (UriResourceNavigation)part;
            
            // We have three scenarios we must handle:
            // Entity(x)/Related, where Related is a 1:N or M:N relationship => result is a collection
            // Entity(x)/Related, where Related is a N:1 or 1:1 relationship => result is a single entity
            // Entity(x)/Related(z), where Related is a 1:N or M:N relationship => result is a single entity
            if (navSegment.getKeyPredicates().isEmpty()) {
                if ( isOne2ManyProperty(rootEntityType,navSegment.getProperty())) {
                    entityStream = readRelatedEntities(rootEntitySet,rootPredicates,navSegment.getProperty(),responseFormat);
                }
                else {
                    // The relation must point to another entity type, so casting should be safe here
                    EdmEntityType resultType = (EdmEntityType)rootEntityType.getNavigationProperty(navSegment.getProperty().getName()).getType();
                    EdmEntitySet resultEntitySet = entitySetFromType(resultType);
                    
                    entityStream = readEntity(resultEntitySet, navSegment.getKeyPredicates(), responseFormat);
                }
            }
            else {
                entityStream = readRelatedEntity(request, rootEntitySet,rootPredicates,navSegment.getProperty(),navSegment.getKeyPredicates(),responseFormat);
            }
            
        }
        else {
            // For now, we'll only allow navigation just to directly linked navs
            throw new ODataApplicationException("[E109] Multi-level navigation not supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        //4. configure the response object
        response.setContent(entityStream);
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());       
        
    }
    

    // Lookup the EntitySet associated with an EntityType
    // In our example, we assume we have only one entityset for each entity type
    private EdmEntitySet entitySetFromType(EdmEntityType type) {
        return serviceMetadata
          .getEdm()
          .getEntityContainer()
          .getEntitySets()
          .stream()
          .filter((s) -> s.getEntityType().getName().equals(type.getName()))
          .findFirst()
          .orElseThrow(() -> new ODataRuntimeException("[E144] No entity set found for type " + type.getFullQualifiedName()));
    }

    // 
    private boolean isOne2ManyProperty(EdmEntityType entityType, EdmNavigationProperty property) {        
        return entityType.getProperty(property.getName()) != null && property.isCollection();
    }

    private InputStream readEntity(EdmEntitySet entitySet, List<UriParameter> keyPredicates,ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
        
        Entity entity = readEntityData(entitySet,keyPredicates);
        
        ContextURL contextUrl = ContextURL.with().entitySet(entitySet).build();
        // expand and select currently not supported
        EntitySerializerOptions options = EntitySerializerOptions
          .with()
          .contextURL(contextUrl)
          .build();

        ODataSerializer serializer = odata.createSerializer(responseFormat);
        
        SerializerResult serializerResult = serializer.entity(serviceMetadata, entitySet.getEntityType(), entity, options);
        return serializerResult.getContent();
    }
    
    private InputStream readRelatedEntities(EdmEntitySet rootEntitySet, List<UriParameter> rootPredicates, EdmNavigationProperty property, ContentType responseFormat) throws ODataApplicationException {

        Object jpaEntity = readJPAEntity(rootEntitySet, rootPredicates);
        try {
            Object set = PropertyUtils.getProperty(jpaEntity, property.getName());
            EdmEntitySet entitySet = entitySetFromType(property.getType());
            ContextURL contextUrl = ContextURL
              .with()
              .entitySet(entitySet)
              .build();
            
            EntityCollectionSerializerOptions options = EntityCollectionSerializerOptions
              .with()
              .contextURL(contextUrl)
              .build();
            
            EntityCollection result = new EntityCollection();
            
            ((Collection<Object>)set)
              .stream()
              .map((o) -> this.entityMapper.map2entity(entitySet, o))
              .forEach((e) -> result.getEntities().add(e));

            ODataSerializer serializer = odata.createSerializer(responseFormat);            
            SerializerResult serializerResult = serializer.entityCollection(serviceMetadata, property.getType(), result, options);
            return serializerResult.getContent();
        }
        catch(Exception ex) {
            throw new ODataRuntimeException("[E181] Error accessing database", ex);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "serial", "unchecked" })
    private InputStream readRelatedEntity(ODataRequest request, EdmEntitySet rootEntitySet, List<UriParameter> rootPredicates, EdmNavigationProperty property, List<UriParameter> predicates, ContentType responseFormat) throws ODataApplicationException, SerializerException {


        JpaSpecificationExecutor<Object> rootRepo =  (JpaSpecificationExecutor<Object>)registry.getRepositoryForEntity(rootEntitySet.getEntityType());
        JpaSpecificationExecutor<Object> repo =  (JpaSpecificationExecutor<Object>)registry.getRepositoryForEntity(property.getType());

        // We assume here that we have a bi-directional 1:N relationship, so we'll
        // always have a property in the child entity that points to the parent
        Class<?> rootClass = ((EdmEntityRepository)rootRepo).getEntityClass();
        Class<?> childClass = ((EdmEntityRepository)repo).getEntityClass();
        SingularAttribute fk = emf.getMetamodel()
          .entity(childClass)
          .getSingularAttributes()
          .stream()
          .filter((attr) -> attr.isAssociation() && attr.getJavaType().isAssignableFrom(rootClass))
          .findFirst()
          .orElse(null);
        
        SingularAttribute pk = emf.getMetamodel()
            .entity(childClass)
            .getId(Long.class);
        
        SingularAttribute rootPk = emf.getMetamodel()
            .entity(rootClass)
            .getId(Long.class);
        
        if ( fk == null ) {
            throw new ODataRuntimeException("[E230] No singular attribute of child class '" + childClass.getName() + "' found" );
        }
        
        
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery q, CriteriaBuilder cb) {
                
                try {
                    Object rootInstance = rootClass.newInstance();
                    PropertyUtils.setProperty(rootInstance, rootPk.getName(), getEntityKey(rootEntitySet.getEntityType(),rootPredicates));
                    
                    final Predicate p = cb.and(
                        cb.equal(
                          root.get(pk),
                          getEntityKey(property.getType(),predicates)),
                        cb.equal(
                          root.get(fk),
                          rootInstance));
                    
                    return p;
                }
                catch(Exception ex) {
                    throw new ODataRuntimeException(ex);
                }
                
            }
         };
            
        // Read data from DB 
        EdmEntitySet relatedEntitySet = entitySetFromType(property.getType());
        EntityCollection data = new EntityCollection();
        
        repo.findAll(spec)
           .stream()
           .forEach((entry) -> data.getEntities().add(entityMapper.map2entity(relatedEntitySet, entry)));

        // 
        
        ODataSerializer serializer = odata.createSerializer(responseFormat);

        // 4th: Now serialize the content: transform from the EntitySet object to InputStream
        EdmEntityType edmEntityType = relatedEntitySet.getEntityType();
        ContextURL contextUrl = ContextURL.with()
            .entitySet(relatedEntitySet)
            .build();

        final String id = request.getRawBaseUri() + "/" + relatedEntitySet.getName();
        EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
            .id(id)
            .contextURL(contextUrl)
            .build();
        SerializerResult serializerResult = serializer.entityCollection(serviceMetadata, edmEntityType, data, opts);
        InputStream serializedContent = serializerResult.getContent();

        
        return serializedContent;
    }
    

    /**
     * This method returns a speficic entity given its primary key
     * @param edmEntitySet
     * @param keyPredicates
     * @return
     */
    protected Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyPredicates) throws ODataApplicationException {
        
        Object jpaEntry = readJPAEntity(edmEntitySet, keyPredicates); 
        Entity e = entityMapper.map2entity(edmEntitySet, jpaEntry);        
        return e;
    }
    
    private Object readJPAEntity(EdmEntitySet edmEntitySet, List<UriParameter> keyPredicates) throws ODataApplicationException {
        EdmEntityType type = edmEntitySet.getEntityType();        
        JpaRepository<Object,Object> repo =  (JpaRepository<Object,Object>)registry.getRepositoryForEntity(type);
        
        // Get key value
        Object keyValue =  getEntityKey(type,keyPredicates);
        Object entry = repo
          .findById(keyValue)
          .orElseThrow(
             () -> new ODataApplicationException("[E116] NO entity found for the given key",
                                       HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH));
       
        return entry;
    }

    private Object getEntityKey(EdmEntityType type, List<UriParameter> keyPredicates) {
        
        if ( keyPredicates.size() > 1 ) {
            throw new ODataRuntimeException("[E131] Composite keys are not supported");
        }
        
        // For now, we'll assume we only have numeric keys.
        UriParameter keyParam = keyPredicates.get(0);
        try {
            return Long.parseLong(keyParam.getText());
        }
        catch(NumberFormatException nfe) {
            throw new ODataRuntimeException("[E140] Invalid key value. Only numeric keys are supported by this service");
        }
        
        
    }

    /* (non-Javadoc)
     * @see org.apache.olingo.server.api.processor.EntityProcessor#createEntity(org.apache.olingo.server.api.ODataRequest, org.apache.olingo.server.api.ODataResponse, org.apache.olingo.server.api.uri.UriInfo, org.apache.olingo.commons.api.format.ContentType, org.apache.olingo.commons.api.format.ContentType)
     */
    @Override
    public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.apache.olingo.server.api.processor.EntityProcessor#updateEntity(org.apache.olingo.server.api.ODataRequest, org.apache.olingo.server.api.ODataResponse, org.apache.olingo.server.api.uri.UriInfo, org.apache.olingo.commons.api.format.ContentType, org.apache.olingo.commons.api.format.ContentType)
     */
    @Override
    public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.apache.olingo.server.api.processor.EntityProcessor#deleteEntity(org.apache.olingo.server.api.ODataRequest, org.apache.olingo.server.api.ODataResponse, org.apache.olingo.server.api.uri.UriInfo)
     */
    @Override
    public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException, ODataLibraryException {
        // TODO Auto-generated method stub

    }

}
