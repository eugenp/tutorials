package org.baeldung.bean.injection.tv;

import org.springframework.beans.factory.annotation.Autowired;

public class MyEntertainmentCenter {

    @Autowired
    private IOCableTVProvider cableTvProvider;

    public MyEntertainmentCenter() {
        cableTvProvider = new VerizonFiosTvImpl();
    }

    public MyEntertainmentCenter(IOCableTVProvider cableTvProvider) {
        this.cableTvProvider = cableTvProvider;
    }

    @Autowired
    public void setCableTvProvider(IOCableTVProvider cableTvProvider) {
        this.cableTvProvider = cableTvProvider;
    }

    public String getChannelLineup() {
        return cableTvProvider.getProgrammingGuide();
    }

}
