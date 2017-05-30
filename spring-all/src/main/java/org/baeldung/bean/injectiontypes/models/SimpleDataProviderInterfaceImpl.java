package org.baeldung.bean.injectiontypes.models;

import org.springframework.stereotype.Service;

/**
 * Created by artpar on 5/23/17.
 */
@Service
public class SimpleDataProviderInterfaceImpl implements DataProviderInterface {
    @Override
    public String getData() {
        return "Bean injection is fun";
    }
}
