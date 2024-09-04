package deep;

public class DeepCopy {
    public static void main(String[] args) throws CloneNotSupportedException {
        Human rima = new Human("Rima");
        Cat originalCat = new Cat(2, true, rima);

        Cat copyCat = (Cat) originalCat.clone();

        System.out.println(originalCat.getAge()); // Output: 2
        System.out.println(copyCat.getAge()); // Output: 2
        System.out.println(originalCat.getOwner().getName()); // Output: Rima
        System.out.println(copyCat.getOwner().getName()); // Output: Rima

        copyCat.setAge(5);

        System.out.println(copyCat.getAge()); // Output: 5
        System.out.println(originalCat.getAge());
        // Output: 2, changing the age of the copy did not affect the age of the
        // original object , and that's because age is a primitive attribute.

        copyCat.getOwner().setName("john");

        System.out.println(copyCat.getOwner().getName()); // Output: John
        System.out.println(originalCat.getOwner().getName()); // Output: Rima
        // the originalCat and copyCat do not share the same object owner,
        // modifying the copyCat's owner does not affect the originalCat's owner.
    }
}