package com.baeldung.restexpress.objectid;

import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import java.util.List;

/**
 * This is the 'service' or 'business logic' layer, where business logic, syntactic and semantic
 * domain validation occurs, along with calls to the persistence layer.
 */
public class SampleOidEntityService {
    private SampleOidEntityRepository samples;

    public SampleOidEntityService(SampleOidEntityRepository samplesRepository) {
        super();
        this.samples = samplesRepository;
    }

    public SampleOidEntity create(SampleOidEntity entity) {
        ValidationEngine.validateAndThrow(entity);
        return samples.create(entity);
    }

    public SampleOidEntity read(Identifier id) {
        return samples.read(id);
    }

    public void update(SampleOidEntity entity) {
        ValidationEngine.validateAndThrow(entity);
        samples.update(entity);
    }

    public void delete(Identifier id) {
        samples.delete(id);
    }

    public List<SampleOidEntity> readAll(QueryFilter filter, QueryRange range, QueryOrder order) {
        return samples.readAll(filter, range, order);
    }

    public long count(QueryFilter filter) {
        return samples.count(filter);
    }
}
