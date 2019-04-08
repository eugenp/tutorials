/**
 * 
 */
package org.baeldung.examples.olingo4;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.server.api.ODataHttpHandler;

/**
 * @author Philippe
 *
 */
public class ODataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ODataHttpHandlerFactory odataHttpHandlerFactory;
    
    
    public ODataServlet(ODataHttpHandlerFactory factory, EntityManagerFactory emf) {
        this.odataHttpHandlerFactory = factory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ODataHttpHandler handler = odataHttpHandlerFactory.newInstance();
        handler.process(req, resp);
    }
}
