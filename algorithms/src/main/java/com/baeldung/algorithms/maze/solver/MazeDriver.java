package com.baeldung.algorithms.maze.solver;

import java.io.File;
import java.util.List;

public class MazeDriver {
    public static void main(String[] args) throws Exception {
        Maze maze = new Maze(new File("src/main/resources/maze/maze2.txt"));
        DFSMazeSolver solver = new DFSMazeSolver();
        List<Coordinate> path = solver.solve(maze);
        maze.printPath(path);
    }
}
