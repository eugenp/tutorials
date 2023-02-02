public class DeepShallowObject {

    Testing testing = new Testing();


    public static void main(String[] args) {
        DeepShallowObject deepShallowObject = new DeepShallowObject();
        deepShallowObject.createAShallowCopy();
        deepShallowObject.createADeppCopy();
    }

    public void createAShallowCopy() {
        Point obj1 = new Point(5, 4);
        Point obj2 = obj1;
        obj2.setY(5);
        System.out.println("Signature of obj1: " + obj1);
        System.out.println("Signature of obj2: " + obj2);
        System.out.println("Values obj1: " + obj1.getX() + "/" + obj1.getY());
        System.out.println("Values obj2: " + obj2.getX() + "/" + obj2.getY());
        testing.assertIfPointsEqual(obj1, obj2);
    }

    public void createADeppCopy() {
        Point obj1 = new Point(5, 4);
        Point obj2 = new Point(obj1.getX(), obj1.getY());
        obj2.setY(5);
        System.out.println("Signature of obj1: " + obj1);
        System.out.println("Signature of obj2: " + obj2);
        System.out.println("obj1 values: " + obj1.getX() + "/" + obj1.getY());
        System.out.println("obj2 values: " + obj2.getX() + "/" + obj2.getY());
        testing.assertIfPointsNotEqual(obj1, obj2);
    }


}
