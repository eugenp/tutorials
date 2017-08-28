package com.baeldung.beaninjectiontypes.beans;

import com.baeldung.beaninjectiontypes.beans.api.Laptop;
import com.baeldung.beaninjectiontypes.beans.api.Processor;
import com.baeldung.beaninjectiontypes.beans.api.VideoGpu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("dell")
public class DellXps extends Laptop {

    @Autowired
    public DellXps(final Processor intel, final @Qualifier("nvdiaGtx") VideoGpu nvdiaGtx) {
        setProcessor(intel);
        setPrimaryGpu(nvdiaGtx);
    }

    @Override
    @Autowired
    @Qualifier("intelOnboardGpu")
    public void setSecondaryGpu(VideoGpu secondaryGpu) {
        super.setSecondaryGpu(secondaryGpu);
    }

    @Override
    public String toString() {
        return "DellXps" + super.toString();
    }
}
