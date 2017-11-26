package com.baeldung.hibernate.spatial;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HibernateSpatialTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        entityManager = JpaUtil.createEntityManager();
        entityManager.setFlushMode(FlushModeType.AUTO);
    }

    @AfterClass
    public static void tearDown() {
        JpaUtil.close();
    }

    @Before
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @After
    public void deleteTestData() {
        entityManager.createQuery("delete from PointEntity").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void shouldConvertWktToGeometry() {
        Geometry geometry = wktToGeometry("POINT (2 5)");
        assertEquals("Point", geometry.getGeometryType());
        assertTrue(geometry instanceof Point);
    }

    @Test
    public void shouldInsertAndSelectPoints() {
        PointEntity entity = new PointEntity();
        entity.setPoint((Point) wktToGeometry("POINT (1 1)"));

        entityManager.persist(entity);
        PointEntity fromDb = entityManager.find(PointEntity.class, entity.getId());
        assertEquals("POINT (1 1)", fromDb.getPoint().toString());
    }

    @Test
    public void shouldSelectDisjointPoints() {
        insertPoint("POINT (1 2)");
        insertPoint("POINT (3 4)");
        insertPoint("POINT (5 6)");

        Point point = (Point) wktToGeometry("POINT (3 4)");
        Query query = entityManager.createQuery("select p from PointEntity p "
          + "where disjoint(p.point, :point) = true", PointEntity.class);
        query.setParameter("point", point);
        assertEquals("POINT (1 2)", ((PointEntity) query.getResultList().get(0)).getPoint().toString());
        assertEquals("POINT (5 6)", ((PointEntity) query.getResultList().get(1)).getPoint().toString());
    }

    @Test
    public void shouldSelectAllPointsWithinPolygon() {
        insertPoint("POINT (1 1)");
        insertPoint("POINT (1 2)");
        insertPoint("POINT (3 4)");
        insertPoint("POINT (5 6)");

        Query query = entityManager.createQuery("select p from PointEntity p where within(p.point, :area) = true",
          PointEntity.class);
        query.setParameter("area", wktToGeometry("POLYGON((0 0, 0 5, 5 5, 5 0, 0 0))"));
        assertEquals(query.getResultList().size(), 3);
        assertEquals("POINT (1 1)", ((PointEntity) query.getResultList().get(0)).getPoint().toString());
        assertEquals("POINT (1 2)", ((PointEntity) query.getResultList().get(1)).getPoint().toString());
        assertEquals("POINT (3 4)", ((PointEntity) query.getResultList().get(2)).getPoint().toString());
    }

    private void insertPoint(String point) {
        PointEntity entity = new PointEntity();
        entity.setPoint((Point) wktToGeometry(point));
        entityManager.persist(entity);
    }

    private Geometry wktToGeometry(String wellKnownText) {
        WKTReader fromText = new WKTReader();
        Geometry geom = null;
        try {
            geom = fromText.read(wellKnownText);
        } catch (ParseException e) {
            throw new RuntimeException("Not a WKT string:" + wellKnownText);
        }
        return geom;
    }
}
