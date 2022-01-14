package com.baeldung.pattern.hexagonal2.domain.adapter.google;

import java.io.Serializable;

public class ItemsDTO implements Serializable {

    private VolumeInfoDTO volumeInfo;

    public ItemsDTO(){

    }

    public VolumeInfoDTO getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfoDTO volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
