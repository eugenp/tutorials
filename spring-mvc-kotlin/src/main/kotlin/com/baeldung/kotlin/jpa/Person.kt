package com.baeldung.jpa

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "person")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        @Column(nullable = false)
        val name: String,
        @Column(nullable = true)
        val email: String?,
        @Column(nullable = true)
        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) val phoneNumbers: List<PhoneNumber>?)