package com.baeldung.sambajcifs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.codelibs.jcifs.smb.CIFSContext;
import org.codelibs.jcifs.smb.context.SingletonContext;
import org.codelibs.jcifs.smb.impl.NtlmPasswordAuthenticator;
import org.codelibs.jcifs.smb.impl.SmbFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SambaJCIFSFiles {

    private static final Logger LOGGER = LoggerFactory.getLogger(SambaJCIFSFiles.class);

    public static void main(String[] args) throws FileNotFoundException, IOException {

        CIFSContext context = SingletonContext.getInstance();

        LOGGER.info("# Creating and deleting a file");
        String fileName = "New_file.txt";
        try (SmbFile file = new SmbFile("smb://192.168.56.101/publicshare/" + fileName, context)) {
            LOGGER.info("About to create file " + file.getName() + "!");
            file.createNewFile();

            LOGGER.info("About to delete file " + file.getName() + "!");
            file.delete();
        }

        LOGGER.info("# Creating and deleting a folder");
        String newFolderName = "New_folder/";
        try (SmbFile newFolder = new SmbFile("smb://192.168.56.101/publicshare/" + newFolderName, context)) {
            LOGGER.info("About to create folder " + newFolder.getName() + "!");
            newFolder.mkdir();

            if (newFolder.exists()) {
                LOGGER.info("Element is file or folder: " + (newFolder.isDirectory() ? "file" : "folder"));
                LOGGER.info("Last modified: " + new Date(newFolder.lastModified()));
            } else {
                LOGGER.info("Folder not found!");
            }

            LOGGER.info("About to delete folder " + newFolder.getName() + "!");
            newFolder.delete();

            if (!newFolder.exists()) {
                LOGGER.info("Folder deleted sucessfully!");
            } else {
                LOGGER.info("Folder deletion failed!");
            }
        }

        LOGGER.info("# Creating and deleting a subfolder with parent");
        newFolderName = "New_folder/";
        String subFolderName = "New_subfolder/";
        try (SmbFile newSubFolder = new SmbFile("smb://192.168.56.101/publicshare/" + newFolderName + subFolderName, context)) {
            LOGGER.info("About to create folder " + newSubFolder.getName() + "!");
            newSubFolder.mkdirs();

            if (newSubFolder.exists()) {
                LOGGER.info("Element is file or folder: " + (newSubFolder.isDirectory() ? "file" : "folder"));
                LOGGER.info("Last modified: " + new Date(newSubFolder.lastModified()));
            } else {
                LOGGER.info("File not found!");
            }
        }

        NtlmPasswordAuthenticator credentials = new NtlmPasswordAuthenticator("WORKGROUP",    // Domain name
            "jane",         // Username
            "Test@Password" // Password
        );

        // Context with authentication
        CIFSContext authContext = context.withCredentials(credentials);

        LOGGER.info("# Copying files with copyTo");
        try (SmbFile source = new SmbFile("smb://192.168.56.101/sambashare/", authContext); //share which needs authentication
            SmbFile dest = new SmbFile("smb://192.168.56.101/publicshare/", context)) { //public share
            source.copyTo(dest);
        }

        LOGGER.info("# Copying files with streams");
        try (InputStream is = new FileInputStream("/home/marcin/test.txt"); //Local file
             SmbFile dest = new SmbFile("smb://192.168.56.101/publicshare/test_copy.txt", context); //Samba resource
             OutputStream os = dest.getOutputStream()) {

            byte[] buffer = new byte[65536]; // using 64KB buffer
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
