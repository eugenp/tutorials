package org.hibernate.caveatemptor.tutorial4.auction.test.web;

import auction.dao.*;
import auction.model.Category;
import org.apache.commons.logging.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;

/**
 * A very simple test servlet that loads and stores an object.
 * <p>
 * Needs a <tt>daoFactoryClass</tt> init parameter. Supports several
 * action events through GET and always updates the HttpSession with
 * the result of the action.
 *
 * @author Christian Bauer
 */
public class FilterTestServlet extends HttpServlet {

    private static Log log = LogFactory.getLog(FilterTestServlet.class);

    public static final String DAO_FACTORY_CLASS = "daoFactoryClass";
    private DAOFactory daoFactory;

    public static final String ACTION                   = "action";

    public static final String ACTION_EVENT_PERSIST     = "persist";
    public static final String ACTION_EVENT_FIND_BYNAME = "find";
    public static final String ACTION_EVENT_MODIFY_NAME = "modify";
    public static final String ACTION_EVENT_DELETE_ALL  = "delete";

    public static final String PARAM_ENTITY_NAME        = "name";
    public static final String PARAM_ENTITY_NEW_NAME    = "newName";
    public static final String CONVERSATION_ENTITY      = "entity";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        CategoryDAO dao = daoFactory.getCategoryDAO();

        String action  = request.getParameter(ACTION);
        String name    = request.getParameter(PARAM_ENTITY_NAME);
        String newName = request.getParameter(PARAM_ENTITY_NEW_NAME);

        if (ACTION_EVENT_PERSIST.equals(action)) {

            // Store a new entity instance and set it in the HttpSession
            Category newEntity = new Category(name);
            dao.makePersistent(newEntity);
            request.getSession().setAttribute(CONVERSATION_ENTITY, newEntity);

        } else if (ACTION_EVENT_MODIFY_NAME.equals(action)) {

            Category old = (Category)request.getSession().getAttribute(CONVERSATION_ENTITY);
            old.setName(newName);
            request.getSession().setAttribute(CONVERSATION_ENTITY, old);

        } else if (ACTION_EVENT_FIND_BYNAME.equals(action)) {

            // Query for an entity instance by name, bypasses the persistence context
            Category example = new Category(name);
            List<Category> result = dao.findByExample(example, "created"); // Ignore "created" property
            Category foundEntity = null;
            if (result.size() > 0) foundEntity = result.get(0);
            request.getSession().setAttribute(CONVERSATION_ENTITY, foundEntity);

        } else if (ACTION_EVENT_DELETE_ALL.equals(action)) {

            Category example = new Category(name);
            List<Category> result = dao.findAll();
            for (Category category : result) dao.makeTransient(category);
            request.getSession().setAttribute(CONVERSATION_ENTITY, null);
        }

    }

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        String daoFactoryName = servletConfig.getInitParameter(DAO_FACTORY_CLASS);
        try {
            if (daoFactoryName != null) {
                log.debug("Testing with DAOFactory: " + daoFactoryName);
                Class daoFactoryClass = Class.forName(daoFactoryName);
                daoFactory = DAOFactory.instance(daoFactoryClass);
            } else {
                throw new ServletException("Configure servlet with a daoFactoryClass parameter!");
            }
        } catch (Exception ex) {
            throw new ServletException("Can't find DAOFactory: " + daoFactoryName);
        }
    }

}
