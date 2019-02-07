package org.baeldung.codility;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class CodilityTest3 {

    // tests

    @Test
    public final void whenSolutionIsCalculated1_thenCorrect() {
        final int[] moves = new int[] { 1, 3, 2, 5, 4, 4, 6, 3, 2 };
        final int solution = solution(moves);
        Assert.assertEquals(7, solution);
    }

    //

    public int solution(final int[] moves) {
        final World world = new World();
        int nextMove = 0;
        for (final int move : moves) {
            switch (nextMove) {
            case 0:
                if (world.moveNorth(move)) {
                    nextMove = 1;
                    break;
                } else {
                    return world.movesCount;
                }
            case 1:
                if (world.moveEast(move)) {
                    nextMove = 2;
                    break;
                } else {
                    return world.movesCount;
                }
            case 2:
                if (world.moveSouth(move)) {
                    nextMove = 3;
                    break;
                } else {
                    return world.movesCount;
                }
            case 3:
                if (world.moveWest(move)) {
                    nextMove = 0;
                    break;
                } else {
                    return world.movesCount;
                }
            default:
                throw new IllegalStateException();
            }
        }

        return world.movesCount;
    }

}

class World {
    int movesCount = 1;
    int currentX = 0;
    int currentY = 0;
    Map<Integer, Integer> minXAtY;
    Map<Integer, Integer> maxXAtY;
    Map<Integer, Integer> minYAtX;
    Map<Integer, Integer> maxYAtX;

    public World() {
        minXAtY = new HashMap<>();
        maxXAtY = new HashMap<>();
        minYAtX = new HashMap<>();
        maxYAtX = new HashMap<>();
    }

    final boolean moveNorth(final int steps) {
        if (isMoveNorthValid(steps)) {
            storeMoveNorth(steps);
            movesCount++;
            return true;
        }
        return false;
    }

    final boolean moveEast(final int steps) {
        if (isMoveEastValid(steps)) {
            storeMoveEast(steps);
            movesCount++;
            return true;
        }
        return false;
    }

    final boolean moveSouth(final int steps) {
        if (isMoveSouthValid(steps)) {
            storeMoveSouth(steps);
            movesCount++;
            return true;
        }
        return false;
    }

    final boolean moveWest(final int steps) {
        if (isMoveWestValid(steps)) {
            storeMoveWest(steps);
            movesCount++;
            return true;
        }
        return false;
    }

    //

    private boolean isMoveNorthValid(final int steps) {
        int currentPosition = currentY;
        for (int i = 1; i <= steps; i++) {
            currentPosition += 1;
            if (minXAtY.get(currentPosition) != null && minXAtY.get(currentPosition) > currentX) {
                return false;
            }
            if (maxXAtY.get(currentPosition) != null && maxXAtY.get(currentPosition) < currentX) {
                return false;
            }
        }
        return true;
    }

    private boolean isMoveEastValid(final int steps) { //
        int currentPosition = currentX;
        for (int i = 1; i <= steps; i++) {
            currentPosition += 1;
            if (minYAtX.get(currentPosition) != null && minYAtX.get(currentPosition) < currentY) {
                return false;
            }
            if (maxYAtX.get(currentPosition) != null && maxYAtX.get(currentPosition) > currentY) {
                return false;
            }
        }
        return true;
    }

    private boolean isMoveSouthValid(final int steps) {
        int currentPosition = currentY;
        for (int i = 1; i <= steps; i++) {
            currentPosition -= 1;
            if (minXAtY.get(currentPosition) != null && minXAtY.get(currentPosition) < currentX) {
                return false;
            }
            if (maxXAtY.get(currentPosition) != null && maxXAtY.get(currentPosition) > currentX) {
                return false;
            }
        }
        return true;
    }

    private boolean isMoveWestValid(final int steps) {
        int currentPosition = currentX;
        for (int i = 1; i <= steps; i++) {
            currentPosition -= 1;
            if (minYAtX.get(currentPosition) != null && minYAtX.get(currentPosition) > currentY) {
                return false;
            }
            if (maxYAtX.get(currentPosition) != null && maxYAtX.get(currentPosition) < currentY) {
                return false;
            }
        }
        return true;
    }

    private void storeMoveNorth(final int steps) {
        currentY = currentY + steps;
        final Integer currentMaxYAtThisLevel = maxYAtX.get(currentX);
        if (currentMaxYAtThisLevel == null || currentMaxYAtThisLevel == null && currentMaxYAtThisLevel < currentY) {
            maxYAtX.put(currentX, currentY);
        }
    }

    private void storeMoveEast(final int steps) {
        currentX = currentX + steps;
        final Integer currentMaxXAtThisLevel = maxXAtY.get(currentY);
        if (currentMaxXAtThisLevel == null || currentMaxXAtThisLevel == null && currentMaxXAtThisLevel < currentX) {
            maxXAtY.put(currentY, currentX);
        }
    }

    private void storeMoveSouth(final int steps) {
        currentY = currentY - steps;
        final Integer currentMinYAtThisLevel = minYAtX.get(currentX);
        if (currentMinYAtThisLevel == null || currentMinYAtThisLevel == null && currentMinYAtThisLevel > currentY) {
            minYAtX.put(currentX, currentY);
        }
    }

    private void storeMoveWest(final int steps) {
        currentX = currentX - steps;
        final Integer currentMinXAtThisLevel = minXAtY.get(currentY);
        if (currentMinXAtThisLevel == null || currentMinXAtThisLevel == null && currentMinXAtThisLevel > currentX) {
            minXAtY.put(currentY, currentX);
        }
    }

}
