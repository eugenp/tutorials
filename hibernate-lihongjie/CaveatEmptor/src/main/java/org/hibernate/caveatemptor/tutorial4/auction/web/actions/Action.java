package org.hibernate.caveatemptor.tutorial4.auction.web.actions;

import java.util.Map;

/**
 * A typical controller interface in an MVC framework.
 *
 * @author Christian Bauer
 */
public interface Action {

    /**
     * Process the request or event.
     *
     * @param event the event to be processed, contains input, might receive output
     * @return String an indicator of the process outcome, used to lookup the forward
     */
    public String execute(Map event);

}
