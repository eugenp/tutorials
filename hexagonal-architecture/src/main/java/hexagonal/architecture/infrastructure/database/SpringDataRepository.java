package hexagonal.architecture.infrastructure.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRepository extends JpaRepository<SimCardEntity, String> {

    Optional<SimCardEntity> findByIccid(String iccid);

    Optional<SimCardEntity> findByIccidAndStatus(String iccid, String status);
}

