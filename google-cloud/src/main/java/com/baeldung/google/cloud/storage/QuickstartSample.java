package com.baeldung.google.cloud.storage;

// Imports the Google Cloud client library
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.nio.charset.StandardCharsets.UTF_8;


@Slf4j
public class QuickstartSample {

    public static void main(String[] args) {

        Storage storage = loadStorageFromPath("google-cloud/src/main/resources/google-auth.json", "logical-utility-781");

        // The name for the new bucket
        String bucketName = "egoebelbecker-1-bucket";  // "my-new-bucket";

        // Creates the new bucket
        Bucket bucket = storage.create(BucketInfo.of(bucketName));


        Blob blob = saveString("Foobar", "Hi there!", bucket);




        System.out.printf("Bucket %s created.%n", bucket.getName());
    }


    private static Storage loadStorageFromDefaultInstance() {

        // Instantiates a client
        return StorageOptions.getDefaultInstance().getService();

    }

    private static Storage loadStorageFromPath(String path, String projectId) {

        try {
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(path));
            return StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build().getService();
        } catch (IOException ioe) {
            log.error("Error loading authorization file ", ioe);
            return null;
        }
    }


    private static Blob saveString(String blobName, String value, Bucket bucket) {

        return bucket.create(blobName, value.getBytes(UTF_8), "text/plain");
    }


    private static String getString(String blobName, Bucket bucket) {





    }


}