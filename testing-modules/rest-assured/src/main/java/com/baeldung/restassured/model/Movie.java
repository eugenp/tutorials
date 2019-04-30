package com.baeldung.restassured.model;

public class Movie {

    private Integer id;

    private String name;

    private String synopsis;

    public Movie() {
    }

    public Movie(Integer id, String name, String synopsis) {
        super();
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
