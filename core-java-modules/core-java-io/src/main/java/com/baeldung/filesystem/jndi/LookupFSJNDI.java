package com.baeldung.filesystem.jndi;

import java.io.File;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LookupFSJNDI {
    private InitialContext ctx = null;

    public LookupFSJNDI() throws NamingException {
        super();
        init();
    }

    private void init() throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        // URI to namespace (actual directory)
        env.put(Context.PROVIDER_URL, "file:./src/test/resources");

        ctx = new InitialContext(env);
    }

    public InitialContext getCtx() {
        return ctx;
    }

    public File getFile(String fileName) {
        File file;
        try {
            file = (File) getCtx().lookup(fileName);
        } catch (NamingException e) {
            file = null;
        }
        return file;
    }

}
