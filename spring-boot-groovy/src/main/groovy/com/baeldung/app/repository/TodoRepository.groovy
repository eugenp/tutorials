package com.baeldung.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import com.baeldung.app.entity.Todo

@Repository
interface TodoRepository extends JpaRepository<Todo, Integer> {}