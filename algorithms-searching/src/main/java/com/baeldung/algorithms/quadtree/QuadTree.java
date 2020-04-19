package com.baeldung.algorithms.quadtree;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private static final int MAX_POINTS = 3;
    private Region area;
    private List<Point> points = new ArrayList<>();
    private List<QuadTree> quadTrees = new ArrayList<>();
    private StringBuilder searchTraversePath;

    public QuadTree(Region area) {
        this.area = area;
    }

    public boolean addPoint(Point point) {
        if (this.area.containsPoint(point)) {
            if (this.points.size() < MAX_POINTS) {
                this.points.add(point);
                return true;
            } else {
                if (this.quadTrees.size() == 0) {
                    createQuadrants();
                }
                return addPointToOneQuadrant(point);
            }
        }
        return false;
    }

    private boolean addPointToOneQuadrant(Point point) {
        boolean isPointAdded;
        for (int i = 0; i < 4; i++) {
            isPointAdded = this.quadTrees.get(i)
                .addPoint(point);
            if (isPointAdded)
                return true;
        }
        return false;
    }

    private void createQuadrants() {
        Region region;
        for (int i = 0; i < 4; i++) {
            region = this.area.getQuadrant(i);
            quadTrees.add(new QuadTree(region));
        }
    }

    public List<Point> search(Region searchRegion, List<Point> matches, String depthIndicator) {
        searchTraversePath = new StringBuilder();
        if (matches == null) {
            matches = new ArrayList<Point>();
            searchTraversePath.append(depthIndicator)
                .append("Search Boundary =")
                .append(searchRegion)
                .append("\n");
        }
        if (!this.area.doesOverlap(searchRegion)) {
            return matches;
        } else {
            for (Point point : points) {
                if (searchRegion.containsPoint(point)) {
                    searchTraversePath.append(depthIndicator)
                    .append("Found match " + point)
                    .append("\n");
                    matches.add(point);
                }
            }
            if (this.quadTrees.size() > 0) {
                for (int i = 0; i < 4; i++) {
                    searchTraversePath.append(depthIndicator)
                        .append("Q")
                        .append(i)
                        .append("-->")
                        .append(quadTrees.get(i).area)
                        .append("\n");
                    quadTrees.get(i)
                        .search(searchRegion, matches, depthIndicator + "\t");
                    this.searchTraversePath.append(quadTrees.get(i)
                        .printSearchTraversePath());
                }
            }
        }
        return matches;
    }

    public String printTree(String depthIndicator) {
        String str = "";
        if (depthIndicator == "") {
            str += "Root-->" + area.toString() + "\n";
        }

        for (Point point : points) {
            str += depthIndicator + point.toString() + "\n";
        }
        for (int i = 0; i < quadTrees.size(); i++) {
            str += depthIndicator + "Q" + String.valueOf(i) + "-->" + quadTrees.get(i).area.toString() + "\n";
            str += quadTrees.get(i)
                .printTree(depthIndicator + "\t");
        }
        return str;
    }

    public String printSearchTraversePath() {
        return searchTraversePath.toString();
    }
}
