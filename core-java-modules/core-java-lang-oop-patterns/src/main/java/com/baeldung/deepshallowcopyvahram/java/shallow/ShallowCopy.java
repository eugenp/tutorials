package shallow;

public class ShallowCopy {
    private int[] arr;

    public ShallowCopy(int[] arr) {
        this.arr = arr;
    }

    public ShallowCopy(ShallowCopy copy) {
        this.arr = copy.arr;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }
}
