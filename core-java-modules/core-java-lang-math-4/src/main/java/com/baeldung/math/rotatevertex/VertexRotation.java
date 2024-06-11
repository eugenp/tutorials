package com.baeldung.math.rotatevertex;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class VertexRotation {
    public static Point2D.Double usingOriginAsRotationPoint(Point2D.Double vertex, Point2D.Double rotationPoint, double angle) {
        double translatedToOriginX = vertex.x - rotationPoint.x;
        double translatedToOriginY = vertex.y - rotationPoint.y;

        double rotatedX = translatedToOriginX * Math.cos(angle) - translatedToOriginY * Math.sin(angle);
        double rotatedY = translatedToOriginX * Math.sin(angle) + translatedToOriginY * Math.cos(angle);

        double reverseTranslatedX = rotatedX + rotationPoint.x;
        double reverseTranslatedY = rotatedY + rotationPoint.y;

        return new Point2D.Double(reverseTranslatedX, reverseTranslatedY);
    }

    public static Point2D.Double usingAffineTransform(Point2D.Double vertex, Point2D.Double rotationPoint, double angle) {
        AffineTransform affineTransform = AffineTransform.getRotateInstance(angle, rotationPoint.x, rotationPoint.y);
        Point2D.Double rotatedVertex = new Point2D.Double();
        affineTransform.transform(vertex, rotatedVertex);
        return rotatedVertex;
    }
}
