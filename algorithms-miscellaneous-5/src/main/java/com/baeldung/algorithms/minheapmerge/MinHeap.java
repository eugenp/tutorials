package com.baeldung.algorithms.minheapmerge;

public class MinHeap {

    HeapNode[] heapNodes;
    int heapSize;

    public MinHeap(HeapNode heapNodes[]) {
        this.heapSize = heapNodes.length;
        this.heapNodes = heapNodes;
        heapifyFromLastLeafsParent();
    }

    void heapifyFromLastLeafsParent() {
        int lastLeafsParentIndex = getParentNodeIndex(heapSize);
        while (lastLeafsParentIndex >= 0) {
            heapify(lastLeafsParentIndex);
            lastLeafsParentIndex--;
        }
    }

    void heapify(int index) {
        int leftNodeIndex = getLeftNodeIndex(index);
        int rightNodeIndex = getRightNodeIndex(index);
        int smallestElementIndex = index;
        if (leftNodeIndex < heapSize && heapNodes[leftNodeIndex].element < heapNodes[index].element)
            smallestElementIndex = leftNodeIndex;
        
        if (rightNodeIndex < heapSize && heapNodes[rightNodeIndex].element < heapNodes[smallestElementIndex].element)
            smallestElementIndex = rightNodeIndex;
        
        if (smallestElementIndex != index) {
            swap(index, smallestElementIndex);
            heapify(smallestElementIndex);
        }
    }

    int getParentNodeIndex(int index) {
        return (index - 1) / 2;
    }

    int getLeftNodeIndex(int index) {
        return (2 * index + 1);
    }

    int getRightNodeIndex(int index) {
        return (2 * index + 2);
    }

    HeapNode getRootNode() {
        return heapNodes[0];
    }

    void hepifyFromRoot(HeapNode root) {
        heapify(0);
    }

    void swap(int i, int j) {
        HeapNode temp = heapNodes[i];
        heapNodes[i] = heapNodes[j];
        heapNodes[j] = temp;
    }
}
