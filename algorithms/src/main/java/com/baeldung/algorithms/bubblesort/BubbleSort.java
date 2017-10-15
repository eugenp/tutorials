package project4;

import java.util.stream.IntStream;

public class BubbleSort {

    public void bubbleSort(Integer[] arr) {
        int n = arr.length;
        IntStream.range(0, n - 1)
            .forEach(i -> {
                IntStream.range(i + 1, n - i)
                    .forEach(j -> {
                        if (arr[j - 1] > arr[j]) {
                            int temp = arr[j];
                            arr[j] = arr[j - 1];
                            arr[j - 1] = temp;
                        }
                    });
            });
    }

    public void optimizedBubbleSort(Integer[] arr) {
        int i = 0, n = arr.length;
        boolean swapNeeded = true;
        while (i < n - 1 && swapNeeded) {
            swapNeeded = false;
            for (int j = i + 1; j < n - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swapNeeded = true;
                }
            }
            if (!swapNeeded)
                break;
            i++;
        }
    }

}
