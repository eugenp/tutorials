package com.baeldung.booking.adapter.out.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id"})
public class AppointmentEntity {
        
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    @Column
    private String withUser;

    @Column
    private Long fromTime;

    @Column
    private Long toTime;

}
