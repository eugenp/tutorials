package shallow;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ShallowCopy obj = new ShallowCopy(new int[]{1,2,3});
        ShallowCopy objCopy = new ShallowCopy(obj);
        obj.getArr()[0] = 0; // or objCopy.getArr()[0] = 0

        System.out.println(obj == objCopy); // false
        System.out.println(obj.getArr() == objCopy.getArr()); // true
        System.out.println(Arrays.toString(obj.getArr())); // [0, 2, 3]
        System.out.println(Arrays.toString(objCopy.getArr())); // [0, 2, 3]
    }
}
