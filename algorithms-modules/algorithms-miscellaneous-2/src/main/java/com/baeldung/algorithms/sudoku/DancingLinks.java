package com.baeldung.algorithms.sudoku;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DancingLinks {

    private ColumnNode header;
    private List<DancingNode> answer;

    private void search(int k) {
        if (header.R == header) {
            handleSolution(answer);
        } else {
            ColumnNode c = selectColumnNodeHeuristic();
            c.cover();

            for (DancingNode r = c.D; r != c; r = r.D) {
                answer.add(r);

                for (DancingNode j = r.R; j != r; j = j.R) {
                    j.C.cover();
                }

                search(k + 1);

                r = answer.remove(answer.size() - 1);
                c = r.C;

                for (DancingNode j = r.L; j != r; j = j.L) {
                    j.C.uncover();
                }
            }
            c.uncover();
        }
    }

    private ColumnNode selectColumnNodeHeuristic() {
        int min = Integer.MAX_VALUE;
        ColumnNode ret = null;
        for (ColumnNode c = (ColumnNode) header.R; c != header; c = (ColumnNode) c.R) {
            if (c.size < min) {
                min = c.size;
                ret = c;
            }
        }
        return ret;
    }

    private ColumnNode makeDLXBoard(boolean[][] grid) {
        final int COLS = grid[0].length;

        ColumnNode headerNode = new ColumnNode("header");
        List<ColumnNode> columnNodes = new ArrayList<>();

        for (int i = 0; i < COLS; i++) {
            ColumnNode n = new ColumnNode(Integer.toString(i));
            columnNodes.add(n);
            headerNode = (ColumnNode) headerNode.hookRight(n);
        }
        headerNode = headerNode.R.C;

        for (boolean[] aGrid : grid) {
            DancingNode prev = null;
            for (int j = 0; j < COLS; j++) {
                if (aGrid[j]) {
                    ColumnNode col = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(col);
                    if (prev == null)
                        prev = newNode;
                    col.U.hookDown(newNode);
                    prev = prev.hookRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = COLS;

        return headerNode;
    }

    DancingLinks(boolean[][] cover) {
        header = makeDLXBoard(cover);
    }

    public void runSolver() {
        answer = new LinkedList<>();
        search(0);
    }

    private void handleSolution(List<DancingNode> answer) {
        int[][] result = parseBoard(answer);
        printSolution(result);
    }

    private int size = 9;

    private int[][] parseBoard(List<DancingNode> answer) {
        int[][] result = new int[size][size];
        for (DancingNode n : answer) {
            DancingNode rcNode = n;
            int min = Integer.parseInt(rcNode.C.name);
            for (DancingNode tmp = n.R; tmp != n; tmp = tmp.R) {
                int val = Integer.parseInt(tmp.C.name);
                if (val < min) {
                    min = val;
                    rcNode = tmp;
                }
            }
            int ans1 = Integer.parseInt(rcNode.C.name);
            int ans2 = Integer.parseInt(rcNode.R.C.name);
            int r = ans1 / size;
            int c = ans1 % size;
            int num = (ans2 % size) + 1;
            result[r][c] = num;
        }
        return result;
    }

    private static void printSolution(int[][] result) {
        int size = result.length;
        for (int[] aResult : result) {
            StringBuilder ret = new StringBuilder();
            for (int j = 0; j < size; j++) {
                ret.append(aResult[j]).append(" ");
            }
            System.out.println(ret);
        }
        System.out.println();
    }
}
