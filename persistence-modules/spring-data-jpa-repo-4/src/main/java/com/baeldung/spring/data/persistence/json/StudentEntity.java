package com.baeldung.spring.data.persistence.json;

import javax.persistence.*;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class StudentEntity {

    @Id
    @Column(name = "student_id", length = 8)
    private String id;

    @Column(name = "admit_year", length = 4)
    private String admitYear;

    @Type(type = "jsonb")
    @Column(name = "address", columnDefinition = "jsonb")
    private Address address;

}