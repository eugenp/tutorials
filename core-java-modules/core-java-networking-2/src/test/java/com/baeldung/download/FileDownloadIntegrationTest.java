package com.baeldung.download;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class FileDownloadIntegrationTest {
    
    static String FILE_URL = "https://s3.amazonaws.com/baeldung.com/Do+JSON+with+Jackson.pdf?__s=vatuzcrazsqopnn7finb";
    static String FILE_NAME = "file.dat";
    static String FILE_MD5_HASH = "CE20E17B1E1FBF65A85E74AC00FA1FD8";
    
    @Test
    public void givenJavaIO_whenDownloadingFile_thenDownloadShouldBeCorrect() throws NoSuchAlgorithmException, IOException {
        
        FileDownload.downloadWithJavaIO(FILE_URL, FILE_NAME);
        assertTrue(checkMd5Hash(FILE_NAME));
    }
    
    @Test
    public void givenJavaNIO_whenDownloadingFile_thenDownloadShouldBeCorrect() throws NoSuchAlgorithmException, IOException {
        
        FileDownload.downloadWithJavaNIO(FILE_URL, FILE_NAME);
        assertTrue(checkMd5Hash(FILE_NAME));
    }
    
    @Test
    public void givenJava7IO_whenDownloadingFile_thenDownloadShouldBeCorrect() throws NoSuchAlgorithmException, IOException {
        
        FileDownload.downloadWithJava7IO(FILE_URL, FILE_NAME);
        assertTrue(checkMd5Hash(FILE_NAME));
    }
    
    @Test
    public void givenAHCLibrary_whenDownloadingFile_thenDownloadShouldBeCorrect() throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
        
        FileDownload.downloadWithAHC(FILE_URL, FILE_NAME);
        assertTrue(checkMd5Hash(FILE_NAME));
    }
    
    @Test
    public void givenApacheCommonsIO_whenDownloadingFile_thenDownloadShouldBeCorrect() throws NoSuchAlgorithmException, IOException {
        
        FileDownload.downloadWithApacheCommons(FILE_URL, FILE_NAME);
        assertTrue(checkMd5Hash(FILE_NAME));
    }
    
    @Test
    public void givenJavaIO_whenDownloadingFileStops_thenDownloadShouldBeResumedCorrectly() throws NoSuchAlgorithmException, IOException, URISyntaxException {
        
        ResumableDownload.downloadFileWithResume(FILE_URL, FILE_NAME);
        assertTrue(checkMd5Hash(FILE_NAME));
    }
    
    private boolean checkMd5Hash(String filename) throws IOException, NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(filename)));
        byte[] digest = md.digest();
        String myChecksum = DatatypeConverter.printHexBinary(digest);
        
        return myChecksum.equalsIgnoreCase(FILE_MD5_HASH);
    }
    
    @BeforeClass
    public static void setup() throws IOException {
        if (Files.exists(Paths.get(FILE_NAME))) {
            Files.delete(Paths.get(FILE_NAME));
        }
    }
    
    @After
    public void cleanup() throws IOException {
        if (Files.exists(Paths.get(FILE_NAME))) {
            Files.delete(Paths.get(FILE_NAME));
        }
    }
}
