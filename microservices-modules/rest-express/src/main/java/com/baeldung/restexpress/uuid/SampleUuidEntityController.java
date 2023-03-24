package com.baeldung.restexpress.uuid;

import com.baeldung.restexpress.Constants;
import com.strategicgains.hyperexpress.builder.DefaultTokenResolver;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.adapter.Identifiers;
import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;

import java.util.List;

/**
 * This is the 'controller' layer, where HTTP details are converted to domain concepts and passed to the service layer.
 * Then service layer response information is enhanced with HTTP details, if applicable, for the response.
 * <p/>
 * This controller demonstrates how to process a MongoDB entity that is identified by a UUID.
 */
public class SampleUuidEntityController {
    private static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
    private SampleUuidEntityService service;

    public SampleUuidEntityController(SampleUuidEntityService sampleService) {
        super();
        this.service = sampleService;
    }

    public SampleUuidEntity create(Request request, Response response) {
        SampleUuidEntity entity = request.getBodyAs(SampleUuidEntity.class, "Resource details not provided");
        SampleUuidEntity saved = service.create(entity);

        // Construct the response for create...
        response.setResponseCreated();

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_UUID_SAMPLE);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, new DefaultTokenResolver()));

        // Return the newly-created resource...
        return saved;
    }

    public SampleUuidEntity read(Request request, Response response) {
        String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
        SampleUuidEntity entity = service.read(Identifiers.UUID.parse(id));
        return entity;
    }

    public List<SampleUuidEntity> readAll(Request request, Response response) {
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        List<SampleUuidEntity> entities = service.readAll(filter, range, order);
        long count = service.count(filter);
        response.setCollectionResponse(range, entities.size(), count);
        return entities;
    }

    public void update(Request request, Response response) {
        String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
        SampleUuidEntity entity = request.getBodyAs(SampleUuidEntity.class, "Resource details not provided");
        entity.setId(Identifiers.UUID.parse(id));
        service.update(entity);
        response.setResponseNoContent();
    }

    public void delete(Request request, Response response) {
        String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
        service.delete(Identifiers.UUID.parse(id));
        response.setResponseNoContent();
    }
}
