package com.baeldung.hibernate;

import com.baeldung.hibernate.pojo.PointEntity;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Query;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

public class HibernateSpatialTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory("hibernate-spatial.properties")
          .openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void shouldConvertWktToGeometry() throws ParseException {
        Geometry geometry = wktToGeometry("POINT (2 5)");
        assertEquals("Point", geometry.getGeometryType());
        assertTrue(geometry instanceof Point);
    }

    @Test
    public void shouldInsertAndSelectPoints() throws ParseException {
        PointEntity entity = new PointEntity();
        entity.setPoint((Point) wktToGeometry("POINT (1 1)"));

        session.persist(entity);
        PointEntity fromDb = session.find(PointEntity.class, entity.getId());
        assertEquals("POINT (1 1)", fromDb.getPoint().toString());
    }

    @Test
    public void shouldSelectDisjointPoints() throws ParseException {
        insertPoint("POINT (1 2)");
        insertPoint("POINT (3 4)");
        insertPoint("POINT (5 6)");

        Point point = (Point) wktToGeometry("POINT (3 4)");
        Query query = session.createQuery("select p from PointEntity p "
          + "where disjoint(p.point, :point) = true", PointEntity.class);
        query.setParameter("point", point);
        assertEquals("POINT (1 2)", ((PointEntity) query.getResultList().get(0)).getPoint().toString());
        assertEquals("POINT (5 6)", ((PointEntity) query.getResultList().get(1)).getPoint().toString());
    }

    @Test
    public void shouldSelectAllPointsWithinPolygon() throws ParseException {
        insertPoint("POINT (1 1)");
        insertPoint("POINT (1 2)");
        insertPoint("POINT (3 4)");
        insertPoint("POINT (5 6)");

        Query query = session.createQuery("select p from PointEntity p where within(p.point, :area) = true",
          PointEntity.class);
        query.setParameter("area", wktToGeometry("POLYGON((0 0, 0 5, 5 5, 5 0, 0 0))"));
        assertThat(query.getResultList().stream().map(p -> ((PointEntity) p).getPoint().toString()))
          .containsOnly("POINT (1 1)", "POINT (1 2)", "POINT (3 4)");
    }

    private void insertPoint(String point) throws ParseException {
        PointEntity entity = new PointEntity();
        entity.setPoint((Point) wktToGeometry(point));
        session.persist(entity);
    }

    private Geometry wktToGeometry(String wellKnownText) throws ParseException {
        WKTReader fromText = new WKTReader();
        Geometry geom = null;
        geom = fromText.read(wellKnownText);
        return geom;
    }
}
