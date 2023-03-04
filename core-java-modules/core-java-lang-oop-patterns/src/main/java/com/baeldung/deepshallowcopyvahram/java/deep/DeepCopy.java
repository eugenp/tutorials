package deep;

public class DeepCopy {
    private int[] arr;

    public DeepCopy(int[] arr) {
        this.arr = arr;
    }

    public DeepCopy(DeepCopy copy) {
        this.arr = new int[copy.getArr().length];

        for (int i = 0; i < arr.length; i++){
            this.arr[i] = copy.arr[i];
        }
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }
}
