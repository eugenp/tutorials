package org.hibernate.caveatemptor.tutorial3.auction.test;

import javax.naming.*;
import java.lang.reflect.Proxy;

/**
 * Some helper methods for testing.
 *
 * @author Christian Bauer
 */
public class TestUtil {

    public static String listJNDITree(String namespace) {
        StringBuffer buffer = new StringBuffer(4096);
        try {
            Context context = new InitialContext(); // From jndi.properties
            if (namespace!= null)
                context = (Context) context.lookup(namespace);
            buffer.append("Namespace: " + namespace +"\n");
            buffer.append("#####################################\n");
            list(context, " ", buffer, true);
            buffer.append("#####################################\n");
        }
        catch (NamingException e) {
            buffer.append("Failed to get InitialContext, " + e.toString(true));
        }
        return buffer.toString();
    }

    private static void list(Context ctx, String indent, StringBuffer buffer, boolean verbose) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            NamingEnumeration ne = ctx.list("");
            while (ne.hasMore()) {
                NameClassPair pair = (NameClassPair) ne.next();

                String name = pair.getName();
                String className = pair.getClassName();
                boolean recursive = false;
                boolean isLinkRef = false;
                boolean isProxy = false;
                Class c = null;
                try {
                    c = loader.loadClass(className);

                    if (Context.class.isAssignableFrom(c))
                        recursive = true;
                    if (LinkRef.class.isAssignableFrom(c))
                        isLinkRef = true;

                    isProxy = Proxy.isProxyClass(c);
                }
                catch (ClassNotFoundException cnfe) {
                    // If this is a $Proxy* class its a proxy
                    if (className.startsWith("$Proxy")) {
                        isProxy = true;
                        // We have to get the class from the binding
                        try {
                            Object p = ctx.lookup(name);
                            c = p.getClass();
                        }
                        catch (NamingException e) {
                            Throwable t = e.getRootCause();
                            if (t instanceof ClassNotFoundException) {
                                // Get the class name from the exception msg
                                String msg = t.getMessage();
                                if (msg != null) {
                                    // Reset the class name to the CNFE class
                                    className = msg;
                                }
                            }
                        }
                    }
                }

                buffer.append(indent + " +- " + name);

                // Display reference targets
                if (isLinkRef) {
                    // Get the
                    try {
                        Object obj = ctx.lookupLink(name);

                        LinkRef link = (LinkRef) obj;
                        buffer.append("[link -> ");
                        buffer.append(link.getLinkName());
                        buffer.append(']');
                    }
                    catch (Throwable t) {
                        buffer.append("invalid]");
                    }
                }

                // Display proxy interfaces
                if (isProxy) {
                    buffer.append(" (proxy: " + pair.getClassName());
                    if (c != null) {
                        Class[] ifaces = c.getInterfaces();
                        buffer.append(" implements ");
                        for (int i = 0; i < ifaces.length; i++) {
                            buffer.append(ifaces[i]);
                            buffer.append(',');
                        }
                        buffer.setCharAt(buffer.length() - 1, ')');
                    } else {
                        buffer.append(" implements " + className + ")");
                    }
                } else if (verbose) {
                    buffer.append(" (class: " + pair.getClassName() + ")");
                }

                buffer.append('\n');
                if (recursive) {
                    try {
                        Object value = ctx.lookup(name);
                        if (value instanceof Context) {
                            Context subctx = (Context) value;
                            list(subctx, indent + " |  ", buffer, verbose);
                        } else {
                            buffer.append(indent + " |   NonContext: " + value);
                            buffer.append('\n');
                        }
                    }
                    catch (Throwable t) {
                        buffer.append("Failed to lookup: " + name + ", errmsg=" + t.getMessage());
                        buffer.append('\n');
                    }
                }
            }
            ne.close();
        }
        catch (NamingException ne) {
            buffer.append("error while listing context " + ctx.toString() + ": " + ne.toString(true));
        }
    }

}
