package org.baeldung.examples.olingo4.processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
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
import org.apache.olingo.server.api.processor.CountEntityCollectionProcessor;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.baeldung.examples.olingo4.repository.RepositoryRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaEntityCollectionProcessor implements CountEntityCollectionProcessor {

    private OData odata;
    private ServiceMetadata serviceMetadata;
    private EntityManagerFactory emf;
    private RepositoryRegistry repositoryRegistry;

    public JpaEntityCollectionProcessor(EntityManagerFactory emf, RepositoryRegistry repositoryRegistry) {
        this.emf = emf;
        this.repositoryRegistry = repositoryRegistry;
    }

    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    @Override
    public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {

        // 1st we have retrieve the requested EntitySet from the uriInfo object (representation of the parsed service URI)
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0); // in our example, the first segment is the EntitySet
        EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

        // 2nd: fetch the data from backend for this requested EntitySetName
        // it has to be delivered as EntitySet object
        EntityCollection entitySet = getData(edmEntitySet, uriInfo);

        // 3rd: create a serializer based on the requested format (json)
        ODataSerializer serializer = odata.createSerializer(responseFormat);

        // 4th: Now serialize the content: transform from the EntitySet object to InputStream
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();
        ContextURL contextUrl = ContextURL.with()
            .entitySet(edmEntitySet)
            .build();

        final String id = request.getRawBaseUri() + "/" + edmEntitySet.getName();
        EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
            .id(id)
            .contextURL(contextUrl)
            .build();
        SerializerResult serializerResult = serializer.entityCollection(serviceMetadata, edmEntityType, entitySet, opts);
        InputStream serializedContent = serializerResult.getContent();

        // Finally: configure the response object: set the body, headers and status code
        response.setContent(serializedContent);
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());

    }

    @Override
    public void countEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException, ODataLibraryException {

        // 1st we have retrieve the requested EntitySet from the uriInfo object (representation of the parsed service URI)
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0); // in our example, the first segment is the EntitySet
        EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

        // 2nd: fetch the data from backend for this requested EntitySetName
        Long count = getCount(edmEntitySet, uriInfo);

        // Finally: configure the response object: set the body, headers and status code
        response.setContent(new ByteArrayInputStream(count.toString()
            .getBytes()));
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, "text/plain");

    }

    private EntityCollection getData(EdmEntitySet edmEntitySet, UriInfo uriInfo) {

        EdmEntityType type = edmEntitySet.getEntityType();
        JpaRepository<?, ?> repo = repositoryRegistry.getRepositoryForEntity(type);
        EntityCollection result = new EntityCollection();

        repo.findAll()
            .stream()
            .forEach((it) -> result.getEntities()
                .add(map2entity(edmEntitySet, it)));

        return result;
    }

    private Entity map2entity(EdmEntitySet edmEntitySet, Object entry) {

        EntityType<?> et = emf.getMetamodel()
            .entity(entry.getClass());


        Entity e = new Entity();
        try {
            // First, we set the entity's primary key
            
            // Now map all properties
            et.getDeclaredSingularAttributes().stream()
              .forEach( (attr) -> {
                 if ( !attr.isAssociation()) {
                     Object v = getPropertyValue(entry,attr.getName());
                     Property p = new Property(null, attr.getName(),ValueType.PRIMITIVE,v);
                     e.addProperty(p);

                     if ( attr.isId()) {
                         e.setId(createId(edmEntitySet.getName(),v));                     
                     }                     
                 }
                 
              });
            
        } catch (Exception ex) {
            throw new ODataRuntimeException("[E141] Unable to create OData entity", ex);
        }

        return e;
    }
    
    private Object getPropertyValue(Object entry, String name) {
        try {
            return PropertyUtils.getProperty(entry,name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ODataRuntimeException("[E141] Unable to read property from entity, property=" + name, e);
        }
    }

    private URI createId(String entitySetName, Object id)  {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("[E177] Unable to create URI", e);
        }
    }

    private Long getCount(EdmEntitySet edmEntitySet, UriInfo uriInfo) {
        // TODO Auto-generated method stub
        return null;
    }

}
