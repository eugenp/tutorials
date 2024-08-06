package com.baeldung.readbodymail;

import jakarta.mail.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReadBodyMailUnitTest {
    @Test
    void givenSinglePartEmailBody_whenReadingEmail_returnText() throws MessagingException, IOException {
        // Mock the necessary objects
        Store mockStore = mock(Store.class);
        Folder mockFolder = mock(Folder.class);
        Message mockMessage = mock(Message.class);

        // Set up the mock behavior
        when(mockStore.getFolder("inbox")).thenReturn(mockFolder);
        when(mockFolder.getMessages()).thenReturn(new Message[]{mockMessage});
        when(mockMessage.getContent()).thenReturn("Test email content");

        // Create a mock session
        Session mockSession = mock(Session.class);
        when(mockSession.getStore("imaps")).thenReturn(mockStore);

        // Create an instance of your service class
        EmailService emailService = new EmailService(mockSession);

        // Call the method to test
        emailService.retrieveEmails();

        // Verify interactions and assert results
        verify(mockFolder).open(Folder.READ_ONLY);
        assertEquals("Test email content", emailService.getLatestEmailContent());
    }

    @Test
    void givenMultiPartEmailBody_whenReadingTextEmail_returnText() throws MessagingException, IOException {
        // Mock BodyPart and Multipart
        Store mockStore = mock(Store.class);
        Folder mockFolder = mock(Folder.class);
        Message mockMessage = mock(Message.class);
        Session mockSession = mock(Session.class);
        Multipart mockMultipart = mock(Multipart.class);
        BodyPart mockBodyPart = mock(BodyPart.class);

        // Set up the mock behavior
        when(mockSession.getStore("imaps")).thenReturn(mockStore);
        when(mockStore.getFolder("inbox")).thenReturn(mockFolder);
        when(mockFolder.getMessages()).thenReturn(new Message[]{mockMessage});
        when(mockMessage.getContent()).thenReturn(mockMultipart);
        when(mockMultipart.getCount()).thenReturn(1);
        when(mockMultipart.getBodyPart(0)).thenReturn(mockBodyPart);
        when(mockBodyPart.getContentType()).thenReturn("text/plain");
        when(mockBodyPart.getContent()).thenReturn("Test plain text");

        // Create an instance of your service class
        EmailService emailService = new EmailService(mockSession);

        // Call the method to test
        emailService.retrieveEmails();

        // Ensure the content is processed correctly
        assertEquals("Test plain text", emailService.getLatestEmailContent());
    }


    @Test
    void givenMultiPartEmailBody_whenReadingEmailWithAttachment_returnAttachmentSaved() throws MessagingException, IOException {
        // Mock Session and BodyPart
        Session mockSession = mock(Session.class);
        BodyPart mockBodyPart = mock(BodyPart.class);

        when(mockBodyPart.getContentType()).thenReturn("application/octet-stream");
        when(mockBodyPart.getFileName()).thenReturn("testfile.txt");
        when(mockBodyPart.getInputStream()).thenReturn(new ByteArrayInputStream("Attachment content".getBytes()));

        EmailService emailService = new EmailService(mockSession);
        emailService.saveAttachment(mockBodyPart);

        // Verify that the file is created and contains the correct content
        File file = new File("attachments/testfile.txt");
        assertTrue(file.exists());
        assertEquals("Attachment content", new String(Files.readAllBytes(file.toPath())));
    }


    @Test
    void givenNestedMultiPartEmailBody_whenReadingEmail_returnCombinedText() throws Exception {
        // Mocking nested Multipart
        Message mockMessage = mock(Message.class);
        Session mockSession = mock(Session.class);

        // Mock inner multipart with multiple body parts
        Multipart innerMultipart = Mockito.mock(Multipart.class);
        Mockito.when(innerMultipart.getCount()).thenReturn(2);

        BodyPart firstBodyPart = Mockito.mock(BodyPart.class);
        Mockito.when(firstBodyPart.getContentType()).thenReturn("text/plain");
        Mockito.when(firstBodyPart.getContent()).thenReturn("Hello Baeldung! ");

        BodyPart secondBodyPart = Mockito.mock(BodyPart.class);
        Mockito.when(secondBodyPart.getContentType()).thenReturn("text/plain");
        Mockito.when(secondBodyPart.getContent()).thenReturn("How are you?");

        Mockito.when(innerMultipart.getBodyPart(0)).thenReturn(firstBodyPart);
        Mockito.when(innerMultipart.getBodyPart(1)).thenReturn(secondBodyPart);

        // Mock outer multipart that contains the inner multipart
        Multipart outerMultipart = Mockito.mock(Multipart.class);
        Mockito.when(outerMultipart.getCount()).thenReturn(1);

        BodyPart outerBodyPart = Mockito.mock(BodyPart.class);
        Mockito.when(outerBodyPart.getContentType()).thenReturn("multipart/mixed");
        Mockito.when(outerBodyPart.getContent()).thenReturn(innerMultipart);

        Mockito.when(outerMultipart.getBodyPart(0)).thenReturn(outerBodyPart);
        Mockito.when(mockMessage.getContent()).thenReturn(outerMultipart);

        // Test extraction of combined text content
        EmailService emailService = new EmailService(mockSession);
        String result = emailService.extractTextContent(mockMessage);

        // Assert the combined extracted content
        assertEquals("Hello Baeldung! How are you?", result);
    }
}
