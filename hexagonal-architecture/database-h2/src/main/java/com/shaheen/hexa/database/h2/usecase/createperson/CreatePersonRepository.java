package com.shaheen.hexa.database.h2.usecase.createperson;

import com.shaheen.hexa.database.h2.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CreatePersonRepository extends JpaRepository<PersonEntity, Long> {

}
