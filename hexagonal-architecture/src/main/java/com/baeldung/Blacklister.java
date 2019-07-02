package com.baeldung;

public class Blacklister {

    private Persistence persistence;
    private Storage travelerStore;

    public Blacklister(Persistence persistence, Storage travelerStore) {
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
