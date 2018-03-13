package org.hibernate.caveatemptor.tutorial4.auction.test.web;

import org.testng.annotations.*;
import auction.web.filter.HibernateSessionConversationFilter;
import auction.test.TestUtil;
import org.hibernate.context.ManagedSessionContext;
import com.meterware.servletunit.*;
import com.meterware.httpunit.*;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * Testing the session-per-request and the session-per-conversation filters.
 * <p>
 * These filteres are for servlets, so this simulates conversations implemented
 * with a <tt>TestServlet</tt>. See web.xml for the configuration of DAOs used
 * internally by the <tt>TestServlet</tt>.
 *
 * @author Christian Bauer
 */
public class FilterTest {

    ServletRunner runner;
    ServletUnitClient client;
    HttpSession httpSession;

    @Parameters({ "webXmlResource" })
    @BeforeClass(groups = "integration-hibernate")
    public void initServletRunner(String webXmlResource) throws Exception {
        InputStream webxml = FilterTest.class.getClassLoader()
                                .getResourceAsStream(webXmlResource);
        runner = new ServletRunner(webxml);
    }

    @AfterClass(groups = "integration-hibernate")
    public void shutdownServletRunner() {
        runner.shutDown();
    }

    @BeforeMethod(groups = "integration-hibernate")
    public void prepareClientAndHttpSession() {
         client = runner.newClient();
         httpSession = client.getSession(true);
    }

    /**
     * Runs a simple session-per-request persistence strategy on a test servlet.
     */
    @Test(groups = "integration-hibernate")
    public void testSessionPerRequest() throws Exception {
        runSessionPerRequestTest();
    }

    /**
     * Runs a conversation with session-per-conversation persistence strateg on a test servlet.
     */
    @Test(groups = "integration-hibernate")
    public void testSessionPerConversation() throws Exception {

        // Setting hibernate.current_session_context_class to 'managed', rebuilds SessionFactory
        String originalSetting = TestUtil.getSessionContextConfigurationSetting();
        TestUtil.enableSessionContext(ManagedSessionContext.class.getName());

        runSessionPerConversationTest();

        // Setting hibernate.current_session_context_class to whatever it was earlier, rebuilds SessionFactory
        TestUtil.enableSessionContext(originalSetting);
    }

    /* ########################## Implementation of the tests ########################## */

    private void runSessionPerRequestTest() throws Exception {

        // Use FilterTestServlet with HibernateSessionRequestFilter enabled (see web.xml)
        WebRequest request = new GetMethodWebRequest("http://ignore.host/session-per-request");

        // We re-use the same entity name
        request.setParameter(FilterTestServlet.PARAM_ENTITY_NAME, "test");

        // First, persist an entity instance in a unit of work
        request.setParameter(FilterTestServlet.ACTION, FilterTestServlet.ACTION_EVENT_PERSIST);
        InvocationContext invocation = client.newInvocation(request);
        invocation.service();
        assert httpSession.getAttribute(FilterTestServlet.CONVERSATION_ENTITY) != null;

        // Null control
        httpSession.setAttribute(FilterTestServlet.CONVERSATION_ENTITY, null);

        // Now check if it is in the database with a new unit of work (query by name)
        request.setParameter(FilterTestServlet.ACTION, FilterTestServlet.ACTION_EVENT_FIND_BYNAME);
        invocation = client.newInvocation(request);
        invocation.service();
        assert httpSession.getAttribute(FilterTestServlet.CONVERSATION_ENTITY) != null;
    }


    private void runSessionPerConversationTest() throws Exception {

        // Use FilterTestServlet with ManagedSessionContext enabled (see web.xml)
        WebRequest request = new GetMethodWebRequest("http://ignore.host/session-per-conversation");

        // First, persist an entity instance
        request.setParameter(FilterTestServlet.ACTION, FilterTestServlet.ACTION_EVENT_PERSIST);
        request.setParameter(FilterTestServlet.PARAM_ENTITY_NAME, "test");
        InvocationContext invocation = client.newInvocation(request);
        invocation.service();
        assert httpSession.getAttribute(FilterTestServlet.CONVERSATION_ENTITY) != null;

        // Modify a persistent instance
        request.setParameter(FilterTestServlet.ACTION, FilterTestServlet.ACTION_EVENT_MODIFY_NAME);
        request.setParameter(FilterTestServlet.PARAM_ENTITY_NAME, "test");
        request.setParameter(FilterTestServlet.PARAM_ENTITY_NEW_NAME, "test2");
        invocation = client.newInvocation(request);
        invocation.service();
        assert httpSession.getAttribute(FilterTestServlet.CONVERSATION_ENTITY) != null;

        // Now check if it is NOT the database by avoiding the still active persistence context (query by name)
        request.setParameter(FilterTestServlet.ACTION, FilterTestServlet.ACTION_EVENT_FIND_BYNAME);
        request.setParameter(FilterTestServlet.PARAM_ENTITY_NAME, "test2");
        invocation = client.newInvocation(request);
        invocation.service();
        assert httpSession.getAttribute(FilterTestServlet.CONVERSATION_ENTITY) == null;

        // Make changes permanent by ending the conversation
        request.setParameter(FilterTestServlet.ACTION, "");
        invocation = client.newInvocation(request);
        invocation.getRequest().setAttribute(HibernateSessionConversationFilter.END_OF_CONVERSATION_FLAG, "true");
        invocation.service();

        // Now check if it is in the database in a new conversation
        request.setParameter(FilterTestServlet.ACTION, FilterTestServlet.ACTION_EVENT_FIND_BYNAME);
        request.setParameter(FilterTestServlet.PARAM_ENTITY_NAME, "test2");
        invocation = client.newInvocation(request);
        invocation.getRequest().setAttribute(HibernateSessionConversationFilter.END_OF_CONVERSATION_FLAG, "true");
        invocation.service();
        assert httpSession.getAttribute(FilterTestServlet.CONVERSATION_ENTITY) != null;

    }


}
