package deep;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        DeepCopy obj = new DeepCopy(new int[]{1,2,3});
        DeepCopy objCopy = new DeepCopy(obj);
        obj.getArr()[0] = 0; // or objCopy.getArr()[0] = 0

        System.out.println(obj == objCopy); // false
        System.out.println(obj.getArr() == objCopy.getArr()); // false
        System.out.println(Arrays.toString(obj.getArr())); // [0, 2, 3]
        System.out.println(Arrays.toString(objCopy.getArr())); // [1, 2, 3]
    }
}
