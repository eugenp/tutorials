package com.baeldung.springwithgroovy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import com.baeldung.springwithgroovy.entity.Todo

@Repository
interface TodoRepository extends JpaRepository<Todo, Integer> {}