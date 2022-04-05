package com.baeldung.spring.data.couchbase2b.repos;

import java.util.Set;

import com.baeldung.spring.data.couchbase.model.Campus;
import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;

public interface CampusRepository extends CrudRepository<Campus, String> {

    @View(designDocument = "campus", viewName = "byName")
    Set<Campus> findByName(String name);

    @Dimensional(dimensions = 2, designDocument = "campus_spatial", spatialViewName = "byLocation")
    Set<Campus> findByLocationNear(Point point, Distance distance);
}
