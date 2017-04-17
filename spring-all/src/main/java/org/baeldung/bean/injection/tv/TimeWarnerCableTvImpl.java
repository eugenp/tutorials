package org.baeldung.bean.injection.tv;

public class TimeWarnerCableTvImpl implements IOCableTVProvider {

    @Override
    public String getProgrammingGuide() { 
        return "Time Warner Cable Guide";
    }

}
