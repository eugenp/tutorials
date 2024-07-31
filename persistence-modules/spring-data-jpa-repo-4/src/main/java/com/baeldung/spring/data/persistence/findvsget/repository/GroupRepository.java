package com.baeldung.spring.data.persistence.findvsget.repository;

import com.baeldung.spring.data.persistence.findvsget.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
