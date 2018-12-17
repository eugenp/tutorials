package com.baeldung.hexagonal;

//Core application implementation
class HatListCore implements HatList {

    private HatStore hatStore;

    public void setHatPersistence(HatPersistence hatPersistence) {
        this.hatStore = new RealHatStore(hatPersistence);
    }

    public HatStore getHatStore() {
        return hatStore;
    }

}
