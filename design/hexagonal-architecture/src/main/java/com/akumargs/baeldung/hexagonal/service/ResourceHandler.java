/**
 *
 */
package com.akumargs.baeldung.hexagonal.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jvnet.hk2.annotations.Contract;

/**
 * ResourceHandler
 * Service that extracts properties from a file and returns a map.
 *
 */
@Contract
public interface ResourceHandler {
    Map<String, String> extract(File file, List<String> properties);
}
