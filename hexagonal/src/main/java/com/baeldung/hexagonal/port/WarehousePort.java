package com.baeldung.hexagonal.port;

import java.util.Collection;

public interface WarehousePort {

    Collection<String> getProductsList();

}
