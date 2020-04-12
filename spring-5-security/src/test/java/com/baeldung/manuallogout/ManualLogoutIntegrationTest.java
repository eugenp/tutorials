package com.baeldung.manuallogout;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.EXECUTION_CONTEXTS;


@RunWith(SpringRunner.class)
public class ManualLogoutTests {

    private static final String CLEAR_SITE_DATA_HEADER = "Clear-Site-Data";

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
            { CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS };

    private static final String HEADER_VALUE = "\"cache\", \"cookies\", \"storage\", \"executionContexts\"";

    @Rule
    public final SpringTestRule spring = new SpringTestRule();

}
