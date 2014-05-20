package org.baeldung.httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpClientMultipartTest {

    private static final String SERVER = "http://cgi-lib.berkeley.edu/ex/fup.cgi";
    private static final String SERVER2 = "http://posttestserver.com/post.php";
    private static final String SERVER3 = "http://postcatcher.in/catchers/53765b0349c306020000077b";
    private static final String SERVER4 = "http://echo.200please.com";
    private static final String SERVER5 = "http://greensuisse.zzl.org/product/dump/dump.php";
    private static final String SERVER6 = "http://www.newburghschools.org/testfolder/dump.php";
    private static HttpClient client;
    private static HttpPost post;
    private static String textFileName;
    private static String imageFileName;
    private static String zipFileName;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        client = HttpClientBuilder.create().build();
        post = new HttpPost(SERVER2 /*"fup.cgi"*/);
        textFileName = ".\temp.txt";
        imageFileName = "image.jpg";
        zipFileName = "zipFile.zip";

    }

    @Before
    public void setUp() throws Exception {

    }

     /* @Test
      public final void whenUploadWithAddPart_thenNoExceptions() throws IOException {

         
          final File file = new File(textFileName);
          final FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
          final StringBody stringBody1 = new StringBody("This is message 1", ContentType.MULTIPART_FORM_DATA);
          final StringBody stringBody2 = new StringBody("This is message 2", ContentType.MULTIPART_FORM_DATA);
          final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
          builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
          builder.addPart("submitted", fileBody);
          builder.addPart("note", stringBody1);
          builder.addPart("note2", stringBody2);
          final HttpEntity entity = builder.build();
          post.setEntity(entity);
          final HttpResponse response = client.execute(post);
          System.out.println(getContent(response));
          Header[] headers = response.getAllHeaders();

          for (Header thisHeader : headers) {
              System.out.println(thisHeader.getName() + ":" + thisHeader.getValue());
          }    
    } */
     /*@Test
     public final void whenUploadWithAddBinaryBodyandAddTextBody_ThenNoExeption() throws ClientProtocolException, IOException {
    
         final File file = new File(textFileName);
         String message = "This is a multipart post";
         MultipartEntityBuilder builder = MultipartEntityBuilder.create();
         builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
         builder.addBinaryBody("submitted", file, ContentType.DEFAULT_BINARY, textFileName);
         builder.addTextBody("note", message, ContentType.TEXT_PLAIN);
         final HttpEntity entity = builder.build();
         post.setEntity(entity);
         HttpResponse response = client.execute(post);
         System.out.println(getContent(response));
         Header[] headers = response.getAllHeaders();

         for (Header thisHeader : headers) {
             System.out.println(thisHeader.getName() + ":" + thisHeader.getValue());
         }

     }*/

    /* @Test
      public final void whenUploadWithAddBinaryBody_NoType_andAddTextBody_ThenNoExeption() throws ClientProtocolException, IOException {

          final File file = new File(imageFileName);
          final String message = "This is a multipart post";
          MultipartEntityBuilder builder = MultipartEntityBuilder.create();
          builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
          builder.addBinaryBody("submitted", file, ContentType.DEFAULT_BINARY, textFileName);
          //builder.addBinaryBody("upfile", fileBin);
          builder.addTextBody("note", message, ContentType.TEXT_PLAIN);
          final HttpEntity entity = builder.build();
          post.setEntity(entity);
          final HttpResponse response = client.execute(post);
          System.out.println(getContent(response));
          Header[] headers = response.getAllHeaders();

          for (Header thisHeader : headers) {
              System.out.println(thisHeader.getName() + ":" + thisHeader.getValue());
          }

      }*/

    /* @Test
     public final void whenUploadWithAddBinaryBody_InputStream_andTextBody_ThenNoException() throws ClientProtocolException, IOException{
         final InputStream inputStream = new FileInputStream(zipFileName);
         final String message = "This is a multipart post";
         final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
         builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
         // builder.addBinaryBody("submitted", inputStream, ContentType.create("application/zip"), "zipFileName");
         builder.addBinaryBody("upfile", inputStream, ContentType.create("application/zip"), "zipFileName");
         builder.addTextBody("note", message, ContentType.TEXT_PLAIN);
         final HttpEntity entity = builder.build();
         post.setEntity(entity);
         final HttpResponse response = client.execute(post);
         
         System.out.println(getContent(response));
         Header[] headers = response.getAllHeaders();

         for (Header thisHeader : headers) {
             System.out.println(thisHeader.getName() + ":" + thisHeader.getValue());
         }

         
     }*/

    // BUG
   @Test
    public final void whenFluentRequestWithBody_ThenNoException() throws IOException{
        
        final String fileName = ".\temp.txt";
        final File fileBin = new File(fileName);
        final String message = "This is a multipart post";
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("upfile", fileBin, ContentType.DEFAULT_BINARY, fileName);
        builder.addTextBody("note", message, ContentType.TEXT_PLAIN);
        final HttpEntity entity = builder.build();
        final Response response = Request.Post(SERVER)
              .body(entity).execute();
    }

    public static String getContent(final HttpResponse response) throws IOException {
       
        final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String body = "";
        String content = "";

        while ((body = rd.readLine()) != null) {
            content += body + "\n";
        }
        return content.trim();
    }
    
    
}
