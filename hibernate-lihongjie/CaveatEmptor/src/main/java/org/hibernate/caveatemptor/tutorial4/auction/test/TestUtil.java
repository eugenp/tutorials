package org.hibernate.caveatemptor.tutorial4.auction.test;

import auction.persistence.HibernateUtil;
import org.hibernate.cfg.*;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Some helper methods useful during testing.
 *
 * @author Christian Bauer
 */
public class TestUtil {

    public static String getSessionContextConfigurationSetting() {
        return HibernateUtil.getConfiguration()
                             .getProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS);
    }

    public static void enableSessionContext(String currentSessionContextClassName) {
        HibernateUtil.getConfiguration()
                        .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS,
                                     currentSessionContextClassName
                        );
        HibernateUtil.rebuildSessionFactory();
    }

    public static void recreateDatabase(Configuration cfg) {
        SchemaExport schemaExport = new SchemaExport(cfg);
        schemaExport.drop(false, true);
        schemaExport.create(false, true);
    }

}
