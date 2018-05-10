package com.baeldung.hibernate.pessimisticlocking;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
public class VersionedStudent {

    @Id
    private Integer studentId;
    private String name;
    private String lastName;
    @Version
    private Timestamp version;

    public VersionedStudent() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public VersionedStudent(Integer studentId, String name, String lastName) {
        super();
        this.studentId = studentId;
        this.name = name;
        this.lastName = lastName;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "VersionedStudent{" + "studentId=" + studentId + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' + ", version=" + version + '}';
    }
}
