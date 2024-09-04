package deep;

public class Cat implements Cloneable{
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
