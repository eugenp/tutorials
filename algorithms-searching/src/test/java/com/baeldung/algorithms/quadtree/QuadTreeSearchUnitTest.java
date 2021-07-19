package com.baeldung.algorithms.quadtree;

import org.junit.Assert;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuadTreeSearchUnitTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QuadTreeSearchUnitTest.class);

    private static QuadTree quadTree;

    @BeforeClass
    public static void setUp() {
        Region area = new Region(0, 0, 400, 400);
        quadTree = new QuadTree(area);

        float[][] points = new float[][] { { 21, 25 }, { 55, 53 }, { 70, 318 }, { 98, 302 }, 
            { 49, 229 }, { 135, 229 }, { 224, 292 }, { 206, 321 }, { 197, 258 }, { 245, 238 } };

        for (int i = 0; i < points.length; i++) {
            Point point = new Point(points[i][0], points[i][1]);
            quadTree.addPoint(point);
        }
        LOGGER.info("\n" + quadTree.printTree(""));
        LOGGER.info("==============================================");
    }

    @Test
    public void givenQuadTree_whenSearchingForRange_thenReturn1MatchingItem() {
        Region searchArea = new Region(200, 200, 250, 250);
        List<Point> result = quadTree.search(searchArea, null, "");
        LOGGER.info(result.toString());
        LOGGER.info(quadTree.printSearchTraversePath());
       
        Assert.assertEquals(1, result.size());
        Assert.assertArrayEquals(new float[] { 245, 238 }, 
            new float[]{result.get(0).getX(), result.get(0).getY() }, 0);
    }
    
    @Test
    public void givenQuadTree_whenSearchingForRange_thenReturn2MatchingItems() {
        Region searchArea = new Region(0, 0, 100, 100);
        List<Point> result = quadTree.search(searchArea, null, "");
        LOGGER.info(result.toString());
        LOGGER.info(quadTree.printSearchTraversePath());
        
        Assert.assertEquals(2, result.size());
        Assert.assertArrayEquals(new float[] { 21, 25 }, 
            new float[]{result.get(0).getX(), result.get(0).getY() }, 0);
        Assert.assertArrayEquals(new float[] { 55, 53 }, 
            new float[]{result.get(1).getX(), result.get(1).getY() }, 0);
        
    }
}
