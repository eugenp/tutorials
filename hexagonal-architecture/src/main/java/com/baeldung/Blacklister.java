package com.baeldung;

public class Blacklister {

    private FilePersistence persistence;
    private TravelerStore travelerStore;

    public Blacklister(FilePersistence persistence, TravelerStore travelerStore) {
        this.persistence = persistence;
        this.travelerStore = travelerStore;
    }

    public void blacklist(Traveler traveler) throws Exception {
        if (travelerStore.storeHasTraveler(traveler)) {
            if (traveler.hasCriminalRecord()) {
                persistence.addToBlacklist(traveler);
            }
        }
    }
}
