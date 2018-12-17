package com.baeldung.hexagonal;

//Core application implementation
class RealHatStore implements HatStore {

    private HatPersistence hatPersistence;

    public RealHatStore(HatPersistence hatPersistence) {
        this.hatPersistence = hatPersistence;
    }

    @Override
    public void add(String name, String hat) {
        // could do things like check we haven't had the name before, but for this
        // example just pass straight through.
        hatPersistence.save(name, hat);
    }

    @Override
    public String getHatFor(String name) {
        return hatPersistence.findByName(name);
    }
};