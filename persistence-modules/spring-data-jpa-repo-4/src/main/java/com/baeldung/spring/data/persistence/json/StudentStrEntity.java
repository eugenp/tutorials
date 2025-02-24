package com.baeldung.spring.data.persistence.json;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "student_str")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class StudentStrEntity {

    @Id
    @Column(name = "student_id", length = 8)
    private String id;

    @Column(name = "admit_year", length = 4)
    private String admitYear;

    @Convert(converter = AddressAttributeConverter.class)
    @Column(name = "address", length = 500)
    private Address address;

}