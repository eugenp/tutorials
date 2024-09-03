package deep;

class Cat implements Cloneable{
    public Cat(int age, boolean isVaccinated, Human owner) {
        this.age = age;
        this.isVaccinated = isVaccinated;
        this.owner = owner;
    }

    protected Object clone() throws CloneNotSupportedException {
        Cat copyCat = (Cat) super.clone();
        copyCat.setOwner(new Human(this.getOwner().getName())); // we are creating a new object human that will be the
        // owner of the copyCat
        return copyCat;
    }

    private int age;
    private boolean isVaccinated;
    private Human owner;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

}

class Human {

    public Human(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}




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