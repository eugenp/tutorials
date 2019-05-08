/**
 * 
 */
package org.baeldung.examples.olingo4.processor;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
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
import org.apache.olingo.server.api.serializer.EntitySerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.baeldung.examples.olingo4.repository.EdmEntityRepository;
import org.baeldung.examples.olingo4.repository.RepositoryRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
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
        
        if ( resourceParts.size() == 1 ) {
            entityStream = readRootEntity(rootEntitySet,rootPredicates,responseFormat);
        }
        else if ( resourceParts.size() == 2 ) {
            UriResource part = resourceParts.get(1);
            if ( !(part instanceof UriResourceNavigation)) {
                throw new ODataRuntimeException("[E103] part type not supported: class=" + part.getClass().getName());
            }

            UriResourceNavigation navSegment = (UriResourceNavigation)part;
            entityStream = readRelatedEntity(request, rootEntitySet,rootPredicates,navSegment.getProperty(),navSegment.getKeyPredicates(),responseFormat);                       
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
   // private boolean isOne2ManyProperty(EdmEntityType entityType, EdmNavigationProperty property) {        
    //    return entityType.getProperty(property.getName()) != null && property.isCollection();
    //}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private InputStream readRootEntity(EdmEntitySet entitySet, List<UriParameter> keyPredicates,ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {        
        EdmEntityType type = entitySet.getEntityType();        
        JpaRepository repo =  registry.getRepositoryForEntity(type);
        
        // Get key value
        Long keyValue =  getEntityKey(keyPredicates);
        Optional<Object> entry  = repo.findById(keyValue);
        if ( !entry.isPresent()) {
            throw new ODataApplicationException(
              "[E116] NO entity found for the given key",
              HttpStatusCode.NOT_FOUND.getStatusCode(), 
              Locale.ENGLISH);
        }
      
        Entity e = entityMapper.map2entity(entitySet, entry.get());        
        return serializeEntity(entitySet,e,responseFormat);        
    }
    
    private InputStream serializeEntity(EdmEntitySet entitySet, Entity entity,ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
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
    
//    @SuppressWarnings("unchecked")
//    protected InputStream readRelatedEntities(EdmEntitySet rootEntitySet, List<UriParameter> rootPredicates, EdmNavigationProperty property, ContentType responseFormat) throws ODataApplicationException {
//
//        Object jpaEntity = readJPAEntity(rootEntitySet, rootPredicates);
//        try {
//            Collection<Object> set = (Collection<Object>)PropertyUtils.getProperty(jpaEntity, property.getName());
//            EdmEntitySet entitySet = entitySetFromType(property.getType());
//            ContextURL contextUrl = ContextURL
//              .with()
//              .entitySet(entitySet)
//              .build();
//            
//            EntityCollectionSerializerOptions options = EntityCollectionSerializerOptions
//              .with()
//              .contextURL(contextUrl)
//              .build();
//            
//            EntityCollection result = new EntityCollection();
//            
//            set.stream()
//              .map((o) -> this.entityMapper.map2entity(entitySet, o))
//              .forEach((e) -> result.getEntities().add(e));
//
//            ODataSerializer serializer = odata.createSerializer(responseFormat);            
//            SerializerResult serializerResult = serializer.entityCollection(serviceMetadata, property.getType(), result, options);
//            return serializerResult.getContent();
//        }
//        catch(Exception ex) {
//            throw new ODataRuntimeException("[E181] Error accessing database", ex);
//        }
//    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private InputStream readRelatedEntity(ODataRequest request, EdmEntitySet entitySet, List<UriParameter> rootPredicates, EdmNavigationProperty property, List<UriParameter> parentPredicates, ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {


        JpaRepository<Object,Object> repo =  (JpaRepository<Object,Object>)registry.getRepositoryForEntity(entitySet.getEntityType());
        EdmEntityRepository<Object> relatedRepo =  (EdmEntityRepository<Object>)registry.getRepositoryForEntity(property.getType());

        // We assume here that we have a bi-directional 1:N relationship, so we'll
        // always have a property in the child entity that points to the parent
        Class<?> rootClass = ((EdmEntityRepository)repo).getEntityClass();        
        Class<?> relatedClass = ((EdmEntityRepository)relatedRepo).getEntityClass();
        
        SingularAttribute fk = emf.getMetamodel()
          .entity(rootClass)
          .getSingularAttributes()
          .stream()
          .filter((attr) -> {
              boolean b = attr.isAssociation() && attr.getJavaType().isAssignableFrom(relatedClass);
              return b;
          })
          .findFirst()
          .orElse(null);
        
        if ( fk == null ) {
            throw new ODataRuntimeException("[E230] No singular attribute of child class '" + relatedClass.getName() + "' found" );
        }
        
        Long pkValue = getEntityKey(rootPredicates);  
        EntityManager em = this.emf.createEntityManager();
        try {
            // Read data from DB 
            Object root = em.find(rootClass, pkValue);            
            Object related = this.entityMapper.getPropertyValue(root, fk.getName());
                
            EdmEntitySet relatedEntitySet = entitySetFromType(property.getType());        
            Entity e = entityMapper.map2entity(relatedEntitySet, related);               
            return serializeEntity(relatedEntitySet,e,responseFormat);
        }
        finally {
            em.close();
        }
    }

//    @SuppressWarnings("unchecked")
//    private Object readJPAEntity(EdmEntitySet edmEntitySet, List<UriParameter> keyPredicates) throws ODataApplicationException {
//        EdmEntityType type = edmEntitySet.getEntityType();        
//        JpaRepository<Object,Object> repo =  (JpaRepository<Object,Object>)registry.getRepositoryForEntity(type);
//        
//        // Get key value
//        Object keyValue =  getEntityKey(type,keyPredicates);
//        Object entry = repo
//          .findById(keyValue)
//          .orElseThrow(
//             () -> new ODataApplicationException("[E116] NO entity found for the given key",
//                                       HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH));
//       
//        return entry;
//    }

    private Long getEntityKey(List<UriParameter> keyPredicates) {
        
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
