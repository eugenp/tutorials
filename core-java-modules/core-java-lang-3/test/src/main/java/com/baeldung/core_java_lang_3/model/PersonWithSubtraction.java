package model;

public class PersonWithSubtraction implements Comparable<PersonWithSubtraction> {

    Integer id;
    String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(PersonWithSubtraction o) {
        return this.id - o.id;
    }

}
