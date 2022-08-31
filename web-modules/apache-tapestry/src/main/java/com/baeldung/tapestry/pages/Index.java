package com.baeldung.tapestry.pages;


import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.slf4j.Logger;

import java.util.Date;

/**
 * Start page of application apache-tapestry.
 */
public class Index {
    @Inject
    private Logger logger;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @Inject
    private Block block;

    // Handle call with an unwanted context
    Object onActivate(EventContext eventContext) {
        return eventContext.getCount() > 0 ?
            new HttpError(404, "Resource not found") :
                null;
    }

    @Log
    void onComplete() {
        logger.info("Complete call on Index page");
    }

    @Log
    void onAjax() {
        logger.info("Ajax call on Index page");

        ajaxResponseRenderer.addRender("middlezone", block);
    }

    public Date getCurrentTime() {
        return new Date();
    }

}
