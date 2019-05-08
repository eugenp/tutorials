package org.baeldung.examples.olingo4;

import org.apache.olingo.server.api.ODataHttpHandler;

public interface ODataHttpHandlerFactory {

    ODataHttpHandler newInstance();
}
