package com.baeldung.annotations;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;

import com.baeldung.persistence.multiple.model.user.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.Transient;

@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "count_by_name", 
        procedureName = "person.count_by_name", 
        parameters = { 
                @StoredProcedureParameter(
                    mode = ParameterMode.IN,
                    name = "name", 
                    type = String.class),
                @StoredProcedureParameter(
                    mode = ParameterMode.OUT, 
                    name = "count", 
                    type = Long.class) 
                }) 
})
public class Person {

    @Id
    private Long id;

    @Transient
    private int age;

    @CreatedBy
    private User creator;

    @LastModifiedBy
    private User modifier;

    @CreatedDate
    private Date createdAt;

    @LastModifiedBy
    private Date modifiedAt;

}
