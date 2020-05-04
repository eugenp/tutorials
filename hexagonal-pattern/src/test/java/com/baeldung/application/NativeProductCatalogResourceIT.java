package com.baeldung.application;

import io.quarkus.test.junit.NativeImageTest;

/**
 * Image native test for ProductCatalog resource.
 */
@NativeImageTest
public class NativeProductCatalogResourceIT extends ProductCatalogResourceUnitTest {

    // Execute the same tests but in native mode.
}