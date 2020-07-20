package com.baeldung.lob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LobSampleRepository extends JpaRepository<LobSample, Long> {

}
