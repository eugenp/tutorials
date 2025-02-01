package com.baeldung.retrievemailfolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;

public class RetrieveEmailFolder {
    public List<String> connectToMailServer(String imapHost, String email, String password) throws Exception {
        Properties properties = getMailProperties(imapHost);

        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore();
        store.connect(email, password);

        List<String> availableFolders = retrieveAvailableFoldersUsingStore(store);
        store.close();
        return availableFolders;
    }

     Properties getMailProperties(String imapHost) {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", imapHost);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");
        return properties;
    }

     List<String> retrieveAvailableFoldersUsingStore(Store store) throws MessagingException {
        List<String> folderList = new ArrayList<>();
        Folder defaultFolder = store.getDefaultFolder();
        listFolders(defaultFolder, folderList);
        return folderList;
    }

     void listFolders(Folder folder, List<String> folderList) throws MessagingException {
        Folder[] subfolders = folder.list();

        if (subfolders.length == 0) {
            folderList.add(folder.getFullName());
        } else {
            for (Folder subfolder : subfolders) {
                listFolders(subfolder, folderList);
            }
        }
    }
}

