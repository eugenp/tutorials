package org.baeldung.bean.injection.tv;

public class VerizonFiosTvImpl implements IOCableTVProvider {

    @Override
    public String getProgrammingGuide() {
        return "Verizon Fios Guide";
    }

}
