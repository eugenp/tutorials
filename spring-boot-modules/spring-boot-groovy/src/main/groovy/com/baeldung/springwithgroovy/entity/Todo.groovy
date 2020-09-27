package com.baeldung.springwithgroovy.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'todo')
class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id
    
    @Column
    String task
    
    @Column
    Boolean isCompleted

}
