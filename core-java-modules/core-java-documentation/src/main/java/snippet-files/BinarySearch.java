
public class BinarySearch {

    public int search(int[] list, int item) {
        int index = Integer.MAX_VALUE;
        int low = 0;
        int high = list.length - 1;
        // @start region="binary"
        while (low <= high) {
            int mid = high - low;
            int guess = list[mid];
            if (guess == item) {
                index = mid;
                break;
            } else if (guess > item) {
                low = mid - 1;
            } else {
                low = mid + 1;
            }
            low++;
        }
        // @end region="binary"

        return index;
    }

}
