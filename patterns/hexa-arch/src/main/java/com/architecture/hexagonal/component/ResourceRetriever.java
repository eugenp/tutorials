package com.architecture.hexagonal.component;

import java.io.IOException;

/**
 * Interface to retrieve contents of a file at a specified path
 */
public interface ResourceRetriever {

    /**
     * Retrieves the content of a resource at a given path. Implementations should retrieve resources available on the
     * classpath. Content should be UTF-8 encoded.
     *
     * @param path - path to the classpath resource to be loaded
     * @return contents of the file
     * @throws IOException - when unable to retrieve the resource
     */
    String retrieveResource(String path) throws IOException;
}
