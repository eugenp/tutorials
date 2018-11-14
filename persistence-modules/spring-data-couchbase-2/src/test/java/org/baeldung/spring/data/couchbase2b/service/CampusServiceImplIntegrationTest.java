package org.baeldung.spring.data.couchbase2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.baeldung.spring.data.couchbase.model.Campus;
import org.baeldung.spring.data.couchbase2b.MultiBucketIntegrationTest;
import org.baeldung.spring.data.couchbase2b.repos.CampusRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

public class CampusServiceImplIntegrationTest extends MultiBucketIntegrationTest {

    @Autowired
    private CampusServiceImpl campusService;

    @Autowired
    private CampusRepository campusRepo;

    private final Campus Brown = Campus.Builder.newInstance().id("campus:Brown").name("Brown").location(new Point(71.4025, 51.8268)).build();

    private final Campus Cornell = Campus.Builder.newInstance().id("campus:Cornell").name("Cornell").location(new Point(76.4833, 42.4459)).build();

    private final Campus Columbia = Campus.Builder.newInstance().id("campus:Columbia").name("Columbia").location(new Point(73.9626, 40.8075)).build();

    private final Campus Dartmouth = Campus.Builder.newInstance().id("campus:Dartmouth").name("Dartmouth").location(new Point(72.2887, 43.7044)).build();

    private final Campus Harvard = Campus.Builder.newInstance().id("campus:Harvard").name("Harvard").location(new Point(71.1167, 42.3770)).build();

    private final Campus Penn = Campus.Builder.newInstance().id("campus:Penn").name("Penn").location(new Point(75.1932, 39.9522)).build();

    private final Campus Princeton = Campus.Builder.newInstance().id("campus:Princeton").name("Princeton").location(new Point(74.6514, 40.3340)).build();

    private final Campus Yale = Campus.Builder.newInstance().id("campus:Yale").name("Yale").location(new Point(72.9223, 41.3163)).build();

    private final Point Boston = new Point(71.0589, 42.3601);
    private final Point NewYorkCity = new Point(74.0059, 40.7128);

    @PostConstruct
    private void loadCampuses() throws Exception {
        campusRepo.save(Brown);
        campusRepo.save(Columbia);
        campusRepo.save(Cornell);
        campusRepo.save(Dartmouth);
        campusRepo.save(Harvard);
        campusRepo.save(Penn);
        campusRepo.save(Princeton);
        campusRepo.save(Yale);
    }

    @Test
    public final void givenNameHarvard_whenFindByName_thenReturnsHarvard() throws Exception {
        Set<Campus> campuses = campusService.findByName(Harvard.getName());
        assertNotNull(campuses);
        assertFalse(campuses.isEmpty());
        assertTrue(campuses.size() == 1);
        assertTrue(campuses.contains(Harvard));
    }

    @Test
    public final void givenHarvardId_whenFind_thenReturnsHarvard() throws Exception {
        Campus actual = campusService.find(Harvard.getId());
        assertNotNull(actual);
        assertEquals(Harvard, actual);
    }

    @Test
    public final void whenFindAll_thenReturnsAll() throws Exception {
        Set<Campus> campuses = campusService.findAll();
        assertTrue(campuses.contains(Brown));
        assertTrue(campuses.contains(Columbia));
        assertTrue(campuses.contains(Cornell));
        assertTrue(campuses.contains(Dartmouth));
        assertTrue(campuses.contains(Harvard));
        assertTrue(campuses.contains(Penn));
        assertTrue(campuses.contains(Princeton));
        assertTrue(campuses.contains(Yale));
    }

    @Test
    public final void whenFindByLocationNearBoston_thenResultContainsHarvard() throws Exception {
        Set<Campus> campuses = campusService.findByLocationNear(Boston, new Distance(1, Metrics.NEUTRAL));
        assertFalse(campuses.isEmpty());
        assertTrue(campuses.contains(Harvard));
        assertFalse(campuses.contains(Columbia));
    }

    @Test
    public final void whenFindByLocationNearNewYorkCity_thenResultContainsColumbia() throws Exception {
        Set<Campus> campuses = campusService.findByLocationNear(NewYorkCity, new Distance(1, Metrics.NEUTRAL));
        assertFalse(campuses.isEmpty());
        assertTrue(campuses.contains(Columbia));
        assertFalse(campuses.contains(Harvard));
    }
}
