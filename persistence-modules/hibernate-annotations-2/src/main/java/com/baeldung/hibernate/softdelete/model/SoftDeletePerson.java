package com.baeldung.hibernate.softdelete.model;

import java.util.List;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.type.YesNoConverter;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
    @NamedNativeQuery(name = "getDeletedPerson", query = "SELECT id, name FROM SoftDeletePerson sdp where sdp.deleted = true", resultClass = SoftDeletePerson.class) })
@SoftDelete
public class SoftDeletePerson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Emails", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "emailId")
    @SoftDelete(strategy = SoftDeleteType.ACTIVE, converter = YesNoConverter.class)
    private List<String> emailIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmailIds() {
        return emailIds;
    }

    public void setEmailIds(List<String> emailIds) {
        this.emailIds = emailIds;
    }

    @Override
    public String toString() {
        return "SoftDeletePerson{" + "id=" + id + ", name='" + name + '\'' + ", emailIds=" + emailIds + '}';
    }
}
