package hexagonal.architecture.infrastructure.database;

import hexagonal.architecture.domain.SimCard;

class SimCardMapper {

    private SimCardMapper() {
    }

    static SimCard toDomain(SimCardEntity simCardEntity) {
        SimCard simCard = new SimCard();
        simCard.setIccid(simCardEntity.getIccid());
        simCard.setMsisdn(simCardEntity.getMsisdn());
        simCard.setPin(simCardEntity.getPin());
        simCard.setPuk(simCardEntity.getPuk());
        simCard.setStatus(simCardEntity.getStatus());
        simCard.setInvalidPinCounter(simCardEntity.getInvalidPinCounter());
        return simCard;
    }

    static SimCardEntity fromDomain(SimCard simCard) {
        SimCardEntity simCardEntity = new SimCardEntity();
        simCardEntity.setIccid(simCard.getIccid());
        simCardEntity.setMsisdn(simCard.getMsisdn());
        simCardEntity.setPin(simCard.getPin());
        simCardEntity.setPuk(simCard.getPuk());
        simCardEntity.setInvalidPinCounter(simCard.getInvalidPinCounter());
        simCardEntity.setStatus(simCard.getStatus());
        return simCardEntity;
    }

}
