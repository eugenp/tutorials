package com.baeldung.hexagonal;

//core application interface
interface HatList {

    HatStore getHatStore();

    void setHatPersistence(HatPersistence hatPersistence);
}
