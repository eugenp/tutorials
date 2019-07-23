package com.baeldung.hexagonal.port;

import java.util.Collection;

public interface DistributorPort {

    Collection<String> getProductList();

}
