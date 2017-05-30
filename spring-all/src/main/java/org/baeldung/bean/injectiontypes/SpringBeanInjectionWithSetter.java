package org.baeldung.bean.injectiontypes;

import org.baeldung.bean.injectiontypes.models.DataProviderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringBeanInjectionWithSetter {
    private DataProviderInterface dataProviderInstance;

    @Autowired
    public void setDataProviderInstance(DataProviderInterface dataProviderInstance) {
        this.dataProviderInstance = dataProviderInstance;
    }

    public String getDataFromDelegate() {
        return dataProviderInstance.getData();
    }
}