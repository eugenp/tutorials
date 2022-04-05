package com.baeldung.spring.data.cassandra.test.repository;

import com.baeldung.spring.data.cassandra.test.domain.Vehicle;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import org.springframework.data.cassandra.repository.Consistency;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Vehicle, String> {

    @Query("select * from vehicles")
    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    List<Vehicle> findAllVehicles();

    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    Optional<Vehicle> findByVin(@Param("vin") String vin);

    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    void deleteByVin(String vin);
}
