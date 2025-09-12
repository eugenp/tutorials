package org.baeldung.recovery.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationJobRepository extends JpaRepository<ApplicationJob, Long> {

}
