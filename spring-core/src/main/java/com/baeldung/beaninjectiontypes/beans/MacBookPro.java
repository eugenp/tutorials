package com.baeldung.beaninjectiontypes.beans;

import com.baeldung.beaninjectiontypes.beans.api.Laptop;
import com.baeldung.beaninjectiontypes.beans.api.Processor;
import com.baeldung.beaninjectiontypes.beans.api.VideoGpu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("macBookPro")
public class MacBookPro extends Laptop {

    @Autowired
    public MacBookPro(final Processor processor, final @Qualifier("intelOnboardGpu") VideoGpu primaryGpu) {
        setProcessor(processor);
        setPrimaryGpu(primaryGpu);
    }

    @Override
    public String toString() {
        return "MacBook Pro" + super.toString();
    }
}
