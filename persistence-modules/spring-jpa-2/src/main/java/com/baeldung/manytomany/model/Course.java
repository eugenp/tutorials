package com.baeldung.manytomany.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany(mappedBy = "likedCourses")
    private Set<Student> likes;

    @OneToMany(mappedBy = "course")
    private Set<CourseRating> ratings;

    @OneToMany(mappedBy = "course")
    private Set<CourseRegistration> registrations;

    // additional properties

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public Set<CourseRating> getRatings() {
        return ratings;
    }

    public Set<CourseRegistration> getRegistrations() {
        return registrations;
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
        Course other = (Course) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
