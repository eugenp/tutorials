package com.baeldung.algorithms.minheapmerge;

public class MinHeapMerge {

    static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    static int populateHeapNodesAndDetermineResultingArrayLength(int[][] array, HeapNode[] heapNodes) {
        int resultSize = 0;

        for (int i = 0; i < array.length; i++) {
            HeapNode node = new HeapNode(array[i][0], i);
            heapNodes[i] = node;
            resultSize += array[i].length;
        }
        return resultSize;
    }

    static int[] createMinHeapAndMergeArrays(int[][] array, HeapNode[] heapNodes, int resultingArraySize) {

        MinHeap minHeap = new MinHeap(heapNodes);
        int[] resultingArray = new int[resultingArraySize];

        for (int i = 0; i < resultingArraySize; i++) {
            HeapNode root = minHeap.getRootNode();
            resultingArray[i] = root.element;

            if (root.nextElementIndex < array[root.arrayIndex].length) {
                root.element = array[root.arrayIndex][root.nextElementIndex++];
            } else {
                root.element = Integer.MAX_VALUE;
            }
            minHeap.hepifyFromRoot(root);
        }
        return resultingArray;
    }

    static void merge(int[][] array) {
        HeapNode[] heapNodes = new HeapNode[array.length];
        int resultingArraySize = populateHeapNodesAndDetermineResultingArrayLength(array, heapNodes);

        int[] resultingArray = createMinHeapAndMergeArrays(array, heapNodes, resultingArraySize);

        printArray(resultingArray);

    }

    public static void main(String args[]) {
        int[][] array = { { 0, 6 }, { 1, 5, 10, 100 }, { 2, 4, 650, 200 }, { 3, 4 }, { 1001, 6456, 23000 } };

        merge(array);
    }
}
