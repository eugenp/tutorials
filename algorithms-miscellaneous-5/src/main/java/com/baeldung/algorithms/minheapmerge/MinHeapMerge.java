package com.baeldung.algorithms.minheapmerge;

public class MinHeapMerge {

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
            minHeap.heapifyFromRoot();
        }
        return resultingArray;
    }

    static int[] merge(int[][] array) {
        HeapNode[] heapNodes = new HeapNode[array.length];
        int resultingArraySize = populateHeapNodesAndDetermineResultingArrayLength(array, heapNodes);
        return createMinHeapAndMergeArrays(array, heapNodes, resultingArraySize);
    }
}
