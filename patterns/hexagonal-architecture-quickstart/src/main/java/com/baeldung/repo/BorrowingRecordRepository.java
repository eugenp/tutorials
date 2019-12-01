package com.baeldung.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pojo.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends CrudRepository<BorrowingRecord,Long> {

}
