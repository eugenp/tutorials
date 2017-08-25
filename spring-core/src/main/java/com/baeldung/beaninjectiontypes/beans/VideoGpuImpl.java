package com.baeldung.beaninjectiontypes.beans;

import com.baeldung.beaninjectiontypes.beans.api.VideoGpu;

public class VideoGpuImpl implements VideoGpu {
    private String name;

    public VideoGpuImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
