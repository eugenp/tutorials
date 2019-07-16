package org.baeldung.spring.data.couchbase2b.service;

import java.util.Set;

import org.baeldung.spring.data.couchbase.model.Campus;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

public interface CampusService {

    Campus find(String id);

    Set<Campus> findByName(String name);

    Set<Campus> findByLocationNear(Point point, Distance distance);

    Set<Campus> findAll();

    void save(Campus campus);
}
