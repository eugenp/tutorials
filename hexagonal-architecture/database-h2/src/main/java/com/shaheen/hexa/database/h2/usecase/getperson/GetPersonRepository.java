package com.shaheen.hexa.database.h2.usecase.getperson;

import com.shaheen.hexa.database.h2.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GetPersonRepository extends JpaRepository<PersonEntity, Long> {
}
