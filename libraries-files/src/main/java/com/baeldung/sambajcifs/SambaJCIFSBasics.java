package com.baeldung.sambajcifs;

import java.net.MalformedURLException;
import java.util.Date;

import org.codelibs.jcifs.smb.CIFSContext;
import org.codelibs.jcifs.smb.context.SingletonContext;
import org.codelibs.jcifs.smb.impl.NtlmPasswordAuthenticator;
import org.codelibs.jcifs.smb.impl.SmbException;
import org.codelibs.jcifs.smb.impl.SmbFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SambaJCIFSBasics {

    private static final Logger LOGGER = LoggerFactory.getLogger(SambaJCIFSBasics.class);

    public static void main(String[] args) throws MalformedURLException, SmbException {

        // Default context
        CIFSContext context = SingletonContext.getInstance();

        LOGGER.info("#  Checking if file exists");
        try (SmbFile file = new SmbFile("smb://192.168.56.101/publicshare/test.txt", context)) {
            if (file.exists()) {
                LOGGER.info("File " + file.getName() + " found!");
            } else {
                LOGGER.info("File " + file.getName() + " not found!");
            }
        }

        NtlmPasswordAuthenticator credentials = new NtlmPasswordAuthenticator("WORKGROUP",    // Domain name
            "jane",         // Username
            "Test@Password" // Password
        );

        // Context with authentication
        CIFSContext authContext = context.withCredentials(credentials);

        LOGGER.info("# Logging in with user and password");
        try (SmbFile res = new SmbFile("smb://192.168.56.101/sambashare/", authContext)) {
            for (String element : res.list()) {
                LOGGER.info("Found element " + element);
            }
        }
        LOGGER.info("# List files and folders in Samba share");
        try (SmbFile res = new SmbFile("smb://192.168.56.101/publicshare/", context)) {
            for (SmbFile element : res.listFiles()) {
                LOGGER.info("Found Samba element of name: " + element.getName());
                LOGGER.info("    Element is file or folder: " + (element.isDirectory() ? "file" : "folder"));
                LOGGER.info("    Length: " + element.length());
                LOGGER.info("    Last modified: " + new Date(element.lastModified()));
            }
        }
    }
}
