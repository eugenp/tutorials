package com.baeldung.minmaxheap;

import java.util.*;

/**
 * Created by arash on 15.06.21.
 */

public class MinMaxHeap<T extends Comparable<T>> {
    private List<T> array;
    private int capacity;
    private int indicator;

    MinMaxHeap(int capacity) {
        array = new ArrayList<>();
        this.capacity = capacity;
        indicator = 1;
    }

    MinMaxHeap(List<T> array) {
        this.array = array;
        this.capacity = array.size();
        this.indicator = array.size() + 1;
    }

    public List<T> getMinMaxHeap() {
        return array;
    }

    public List<T> create() {
        for (int i = Math.floorDiv(array.size(), 2); i >= 1; i--) {
            pushDown(array, i);
        }
        return array;
    }

    private void pushDown(List<T> array, int i) {
        if (isEvenLevel(i)) {
            pushDownMin(array, i);
        } else {
            pushDownMax(array, i);
        }
    }

    private void pushDownMin(List<T> h, int i) {
        while (getLeftChildIndex(i) < indicator) {
            int indexOfSmallest = getIndexOfSmallestChildOrGrandChild(h, i);
            if (h.get(indexOfSmallest - 1).compareTo(h.get(i - 1)) < 0) {
                if (getParentIndex(getParentIndex(indexOfSmallest)) == i) {
                    if (h.get(indexOfSmallest - 1).compareTo(h.get(i - 1)) < 0) {
                        swap(indexOfSmallest - 1, i - 1, h);
                        if (h.get(indexOfSmallest - 1).compareTo(h.get(getParentIndex(indexOfSmallest) - 1)) > 0) {
                            swap(indexOfSmallest - 1, getParentIndex(indexOfSmallest) - 1, h);
                        }
                    }
                } else if (h.get(indexOfSmallest - 1).compareTo(h.get(i - 1)) < 0) {
                    swap(indexOfSmallest - 1, i - 1, h);
                }
            } else {
                break;
            }
            i = indexOfSmallest;
        }
    }

    private void pushDownMax(List<T> h, int i) {
        while (getLeftChildIndex(i) < indicator) {
            int indexOfGreatest = getIndexOfGreatestChildOrGrandChild(h, i);
            if (h.get(indexOfGreatest - 1).compareTo(h.get(i - 1)) > 0) {
                if (getParentIndex(getParentIndex(indexOfGreatest)) == i) {
                    if (h.get(indexOfGreatest - 1).compareTo(h.get(i - 1)) > 0) {
                        swap(indexOfGreatest - 1, i - 1, h);
                        if (h.get(indexOfGreatest - 1).compareTo(h.get(getParentIndex(indexOfGreatest) - 1)) < 0) {
                            swap(indexOfGreatest - 1, getParentIndex(indexOfGreatest) - 1, h);
                        }
                    }
                } else if (h.get(indexOfGreatest - 1).compareTo(h.get(i - 1)) > 0) {
                    swap(indexOfGreatest - 1, i - 1, h);
                }
            } else {
                break;
            }
            i = indexOfGreatest;
        }
    }

    private void swap(int i, int j, List<T> h) {    //switch data at x with data at y
        T temp = h.get(i);
        h.set(i, h.get(j));
        h.set(j, temp);
    }

    private int getLeftChildIndex(int i) {
        return 2 * i;
    }

    private int getRightChildIndex(int i) {
        return ((2 * i) + 1);
    }

    private int getParentIndex(int i) {
        return i / 2;
    }

    private int getGrandparentIndex(int i) {
        return i / 4;
    }

    private boolean isEvenLevel(int i) {
        return logBase2(i) % 2 == 0;
    }

    private int logBase2(int num) {
        return (int) (Math.log(num) / Math.log(2));
    }

    private int getMinChildIndex(int i) {
        return array.get(getLeftChildIndex(i)).compareTo(array.get(getRightChildIndex(i))) < 0 ? getLeftChildIndex(i) : getRightChildIndex(i);
    }

    private int getMaxChildIndex(int i) {
        return array.get(getLeftChildIndex(i)).compareTo(array.get(getRightChildIndex(i))) > 0 ? getLeftChildIndex(i) : getRightChildIndex(i);
    }

    private int getIndexOfSmallestChildOrGrandChild(List<T> h, int i) {
        int minIndex = getLeftChildIndex(i);
        T minValue = h.get(minIndex - 1);

        if (getRightChildIndex(i) < indicator) {
            if (h.get(getRightChildIndex(i) - 1).compareTo(minValue) < 0) {
                minValue = h.get(getRightChildIndex(i));
                minIndex = getRightChildIndex(i);
            }
        } else {
            return minIndex;
        }

        if (getLeftChildIndex(getLeftChildIndex(i)) < indicator) {
            if (h.get(getLeftChildIndex(getLeftChildIndex(i)) - 1).compareTo(minValue) < 0) {
                minValue = h.get(getLeftChildIndex(getLeftChildIndex(i)) - 1);
                minIndex = getLeftChildIndex(getLeftChildIndex(i));
            }
        } else {
            return minIndex;    //if no leftmost grandchild
        }

        if (getRightChildIndex(getLeftChildIndex(i)) < indicator) {
            if (h.get(getRightChildIndex(getLeftChildIndex(i)) - 1).compareTo(minValue) < 0) {
                minValue = h.get(getRightChildIndex(getLeftChildIndex(i)) - 1);
                minIndex = getRightChildIndex(getLeftChildIndex(i));
            }
        } else {
            return minIndex; //if no left-right grandchild
        }

        if (getLeftChildIndex(getRightChildIndex(i)) < indicator) {
            if (h.get(getLeftChildIndex(getRightChildIndex(i)) - 1).compareTo(minValue) < 0) {
                minValue = h.get(getLeftChildIndex(getRightChildIndex(i)) - 1);
                minIndex = getLeftChildIndex(getRightChildIndex(i));
            }
        } else {
            return minIndex; //if no right-left grandchild
        }

        if (getRightChildIndex(getRightChildIndex(i)) < indicator) {
            if (h.get(getRightChildIndex(getRightChildIndex(i)) - 1).compareTo(minValue) < 0) {
                minValue = h.get(getRightChildIndex(getRightChildIndex(i)) - 1);
                minIndex = getRightChildIndex(getRightChildIndex(i));
            }
        } else {
            return minIndex;
        }

        return minIndex;
    }

    private int getIndexOfGreatestChildOrGrandChild(List<T> h, int i) {
        int maxIndex = getLeftChildIndex(i);    //we know left child exists
        T maxValue = h.get(maxIndex - 1);

        if (getRightChildIndex(i) < indicator) {
            if (h.get(getRightChildIndex(i) - 1).compareTo(maxValue) > 0) {
                maxValue = h.get(getRightChildIndex(i) - 1);
                maxIndex = getRightChildIndex(i);
            }
        } else {
            return maxIndex;
        }

        if (getLeftChildIndex(getLeftChildIndex(i)) < indicator) {
            if (h.get(getLeftChildIndex(getLeftChildIndex(i)) - 1).compareTo(maxValue) > 0) {
                maxValue = h.get(getLeftChildIndex(getLeftChildIndex(i)) - 1);
                maxIndex = getLeftChildIndex(getLeftChildIndex(i));
            }
        } else {
            return maxIndex; //if no leftmost grandchild
        }

        if (getRightChildIndex(getLeftChildIndex(i)) < indicator) {
            if (h.get(getRightChildIndex(getLeftChildIndex(i)) - 1).compareTo(maxValue) > 0) {
                maxValue = h.get(getRightChildIndex(getLeftChildIndex(i)) - 1);
                maxIndex = getRightChildIndex(getLeftChildIndex(i));
            }
        } else {
            return maxIndex; //if no left-right grandchild
        }

        if (getLeftChildIndex(getRightChildIndex(i)) < indicator) {
            if (h.get(getLeftChildIndex(getRightChildIndex(i)) - 1).compareTo(maxValue) > 0) {
                maxValue = h.get(getLeftChildIndex(getRightChildIndex(i)) - 1);
                maxIndex = getLeftChildIndex(getRightChildIndex(i));
            }
        } else {
            return maxIndex;
        }

        if (getRightChildIndex(getRightChildIndex(i)) < indicator) {
            if (h.get(getRightChildIndex(getRightChildIndex(i)) - 1).compareTo(maxValue) > 0) {
                maxValue = h.get(getRightChildIndex(getRightChildIndex(i)) - 1);
                maxIndex = getRightChildIndex(getRightChildIndex(i));
            }
        } else {
            return maxIndex;
        }

        return maxIndex;
    }


    public boolean isFull() {
        return indicator == this.capacity + 1;
    }

    public boolean isEmpty() {
        return indicator == 1;
    }

    public void insert(T item) {
        if (isEmpty()) {
            array.add(item);
            indicator++;
        } else if (!isFull()) {
            array.add(item);
            pushUp(array, indicator);
            indicator++;
        } else {
            throw new RuntimeException("invalid operation !!!");
        }
    }

    private void pushUpMin(List<T> h, int i) {
        while (hasGrandparent(i) && h.get(i - 1).compareTo(h.get(getGrandparentIndex(i) - 1)) < 0) {
            swap(i - 1, getGrandparentIndex(i) - 1, h);
            i = getGrandparentIndex(i);
        }
    }

    private void pushUpMax(List<T> h, int i) {
        while (hasGrandparent(i) && h.get(i - 1).compareTo(h.get(getGrandparentIndex(i) - 1)) > 0) {
            swap(i - 1, getGrandparentIndex(i) - 1, h);
            i = getGrandparentIndex(i);
        }
    }

    private boolean hasGrandparent(int i) {
        return getParentIndex(i) > 1;
    }

    private void pushUp(List<T> h, int i) {
        if (i != 1) {
            if (isEvenLevel(i)) {
                if (h.get(i - 1).compareTo(h.get(getParentIndex(i) - 1)) < 0) {
                    pushUpMin(h, i);
                } else {
                    swap(i - 1, getParentIndex(i) - 1, h);
                    i = getParentIndex(i);
                    pushUpMax(h, i);
                }
            } else if (h.get(i - 1).compareTo(h.get(getParentIndex(i) - 1)) > 0) {
                pushUpMax(h, i);
            } else {
                swap(i - 1, getParentIndex(i) - 1, h);
                i = getParentIndex(i);
                pushUpMin(h, i);
            }
        }
    }

    public T min() {
        if (!isEmpty()) {
            return array.get(0);
        }
        return null;
    }

    public T max() {
        if (!isEmpty()) {
            if (indicator == 2) {
                return array.get(0);
            }
            if (indicator == 3) {
                return array.get(1);
            }
            return array.get(1).compareTo(array.get(2)) < 0 ? array.get(2) : array.get(1);
        }
        return null;
    }

    public T removeMin() {
        T min = min();
        if (min != null) {
            if (indicator == 2) {
                array.remove(indicator--);
                return min;
            }
            array.set(0, array.get(--indicator - 1));
            array.remove(indicator - 1);
            pushDown(array, 1);
        }
        return min;
    }

    public T removeMax() {
        T max = max();
        if (max != null) {
            int maxIndex;
            if (indicator == 2) {
                maxIndex = 0;
                array.remove(--indicator - 1);
                return max;
            } else if (indicator == 3) {
                maxIndex = 1;
                array.remove(--indicator - 1);
                return max;
            } else {
                maxIndex = array.get(1).compareTo(array.get(2)) < 0 ? 2 : 1;
            }
            array.set(maxIndex, array.get(--indicator - 1));
            array.remove(indicator - 1);
            pushDown(array, maxIndex + 1);
        }
        return max;
    }
}
