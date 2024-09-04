package shallow;

public class Cat implements Cloneable{
    public Cat(int age, boolean isVaccinated, Human owner) {
        this.age = age;
        this.isVaccinated = isVaccinated;
        this.owner = owner;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // super.clone() by default performs a shallow copy
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