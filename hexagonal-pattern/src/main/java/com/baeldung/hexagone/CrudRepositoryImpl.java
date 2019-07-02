package com.baeldung.hexagone;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CrudRepositoryImpl extends CrudRepository<User,Integer> {

}
