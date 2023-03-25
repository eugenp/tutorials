public class ShallowCopy implements Cloneable {
    public int value;
    public int[] data;

    public ShallowCopy(int value, int[] data) {
        this.value = value;
        this.data = data;
    }

    @Override
    public ShallowCopy clone() throws CloneNotSupportedException {
        return (ShallowCopy) super.clone();
    }

}