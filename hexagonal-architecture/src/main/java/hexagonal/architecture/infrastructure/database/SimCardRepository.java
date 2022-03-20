package hexagonal.architecture.infrastructure.database;

import static hexagonal.architecture.exception.ErrorMessage.ERROR_INVALID_ICCID;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import hexagonal.architecture.domain.SimCard;
import hexagonal.architecture.domain.SimCards;

@Repository
public class SimCardRepository implements SimCards {

    private final SpringDataRepository repository;

    public SimCardRepository(SpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public SimCard find(String iccid) {
        SimCardEntity simCardEntityOptional = repository.findByIccid(iccid)
            .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_ICCID));
        return SimCardMapper.toDomain(simCardEntityOptional);
    }

    @Override
    public void update(SimCard simCard) {
        repository.save(SimCardMapper.fromDomain(simCard));
    }

    @Override
    public boolean isBlocked(String iccid) {
        Optional<SimCardEntity> simCardEntity = repository.findByIccidAndStatus(iccid, SimCard.Status.ACTIVE.name());
        return !simCardEntity.isPresent();
    }
}