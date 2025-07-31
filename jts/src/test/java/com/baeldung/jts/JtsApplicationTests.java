package com.baeldung.jts;

import com.baeldung.jts.operations.JTSOperationUtils;
import com.baeldung.jts.utils.GeometryFactoryUtil;
import org.junit.Assert;
import org.junit.Test;
import org.locationtech.jts.geom.Geometry;

public class JtsApplicationTests {
    @Test
    public void givenPolygon2D_whenContainPoint_thenContainmentIsTrueUnitTest() throws Exception {
        Geometry point = GeometryFactoryUtil.readWKT("POINT (10 20)");
        Geometry polygon = GeometryFactoryUtil.readWKT("POLYGON ((0 0, 0 40, 40 40, 40 0, 0 0))");
        Assert.assertTrue(JTSOperationUtils.isContainment(point, polygon));
    }

    @Test
    public void givenRectangle1_whenIntersectWithRectangle2_thenIntersectionIsTrueUnitTest() throws Exception {
        Geometry rectangle1 = GeometryFactoryUtil.readWKT("POLYGON ((10 10, 10 30, 30 30, 30 10, 10 10))");
        Geometry rectangle2 = GeometryFactoryUtil.readWKT("POLYGON ((20 20, 20 40, 40 40, 40 20, 20 20))");
        Assert.assertTrue(JTSOperationUtils.checkIntersect(rectangle1, rectangle2));
    }

    @Test
    public void givenPoint_whenAddedBuffer_thenPointIsInsideTheBufferUnitTest() throws Exception {
        Geometry point = GeometryFactoryUtil.readWKT("POINT (10 10)");
        Geometry bufferArea = JTSOperationUtils.getBuffer(point, 5);
        Assert.assertTrue(JTSOperationUtils.isContainment(point, bufferArea));
    }

    @Test
    public void givenTwoPoints_whenGetDistanceBetween_thenGetTheDistanceUnitTest() throws Exception {
        Geometry point1 = GeometryFactoryUtil.readWKT("POINT (10 10)");
        Geometry point2 = GeometryFactoryUtil.readWKT("POINT (13 14)");
        double distance = JTSOperationUtils.getDistance(point1, point2);
        double expectedResult = 5.00;
        double delta = 0.00;
        Assert.assertEquals(expectedResult, distance, delta);
    }

    @Test
    public void givenTwoGeometries_whenGetUnionOfBoth_thenGetTheUnionUnitTest() throws Exception {
        Geometry geometry1 = GeometryFactoryUtil.readWKT("POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))");
        Geometry geometry2 = GeometryFactoryUtil.readWKT("POLYGON ((10 0, 10 10, 20 10, 20 0, 10 0))");

        Geometry union = JTSOperationUtils.getUnion(geometry1, geometry2);
        Geometry expectedResult = GeometryFactoryUtil.readWKT("POLYGON ((0 0, 0 10, 10 10, 20 10, 20 0, 10 0, 0 0))");
        Assert.assertEquals(expectedResult, union);
    }

    @Test
    public void givenBaseRectangle_whenAnotherRectangleOverlapping_GetTheDifferenceRectangleUnitTest() throws Exception {
        Geometry base = GeometryFactoryUtil.readWKT("POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))");
        Geometry cut = GeometryFactoryUtil.readWKT("POLYGON ((5 0, 5 10, 10 10, 10 0, 5 0))");

        Geometry result = JTSOperationUtils.getDifference(base, cut);
        Geometry expectedResult = GeometryFactoryUtil.readWKT("POLYGON ((0 0, 0 10, 5 10, 5 0, 0 0))");
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void givenInvalidGeometryValue_whenValidated_thenGiveFixedResultUnitTest() throws Exception {
        Geometry invalidGeo = GeometryFactoryUtil.readWKT("POLYGON ((0 0, 5 5, 5 0, 0 5, 0 0))");
        Geometry result = JTSOperationUtils.validateAndRepair(invalidGeo);

        Geometry expectedResult = GeometryFactoryUtil.readWKT("POLYGON ((2.5 2.5, 5 5, 5 0, 2.5 2.5))");
        Assert.assertEquals(expectedResult, result);
    }
}
