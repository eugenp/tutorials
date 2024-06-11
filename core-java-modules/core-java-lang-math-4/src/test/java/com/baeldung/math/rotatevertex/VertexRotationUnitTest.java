package com.baeldung.math.rotatevertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

public class VertexRotationUnitTest {
    @Test
    void givenRotationPoint_whenUseOrigin_thenRotateVertex() {
        Point2D.Double vertex = new Point2D.Double(2.0, 2.0);
        Point2D.Double rotationPoint = new Point2D.Double(0.0, 1.0);
        double angle = Math.toRadians(45.0);
        Point2D.Double rotatedVertex = VertexRotation.usingOriginAsRotationPoint(vertex, rotationPoint, angle);

        assertEquals(0.707, rotatedVertex.getX(), 0.001);
        assertEquals(3.121, rotatedVertex.getY(), 0.001);
    }

    @Test
    void givenRotationPoint_whenUseAffineTransform_thenRotateVertex() {
        Point2D.Double vertex = new Point2D.Double(2.0, 2.0);
        Point2D.Double rotationPoint = new Point2D.Double(0.0, 1.0);
        double angle = Math.toRadians(45.0);
        Point2D.Double rotatedVertex = VertexRotation.usingAffineTransform(vertex, rotationPoint, angle);

        assertEquals(0.707, rotatedVertex.getX(), 0.001);
        assertEquals(3.121, rotatedVertex.getY(), 0.001);
    }
}
