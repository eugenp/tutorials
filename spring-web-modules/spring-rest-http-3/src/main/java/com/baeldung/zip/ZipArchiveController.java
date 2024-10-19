package com.baeldung.zip;

import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ZipArchiveController {

    @GetMapping(value = "/zip-archive", produces = "application/zip")
    public ResponseEntity<byte[]> getZipBytes() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
        zipOutputStream.setLevel(0);

        addFilesToArchive(zipOutputStream);

        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);

        return ResponseEntity
          .ok()
          .header("Content-Disposition", "attachment; filename=\"files.zip\"")
          .body(byteArrayOutputStream.toByteArray());
    }

    @GetMapping(value = "/zip-archive-stream", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> getZipStream() {

        return ResponseEntity
          .ok()
          .header("Content-Disposition", "attachment; filename=\"files.zip\"")
          .body(out -> {
              ZipOutputStream zipOutputStream = new ZipOutputStream(out);
              zipOutputStream.setLevel(9);
              addFilesToArchive(zipOutputStream);
          });
    }

    @GetMapping(value = "/zip-archive-stream-secured", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> getZipSecuredStream() {

        return ResponseEntity
          .ok()
          .header("Content-Disposition", "attachment; filename=\"files.zip\"")
          .body(out -> {
              net.lingala.zip4j.io.outputstream.ZipOutputStream zipOutputStream =
                new net.lingala.zip4j.io.outputstream.ZipOutputStream(out, "password".toCharArray());

              addFilesToArchive(zipOutputStream);
          });
    }

    void addFilesToArchive(net.lingala.zip4j.io.outputstream.ZipOutputStream zipOutputStream) throws IOException {
        List<String> filesNames = new ArrayList<>();
        filesNames.add("first-file.txt");
        filesNames.add("second-file.txt");

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
        zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
        zipParameters.setEncryptFiles(true);

        for (String fileName : filesNames) {
            File file = new File(ZipArchiveController.class.getClassLoader()
              .getResource(fileName).getFile());

            zipParameters.setFileNameInZip(file.getName());
            zipOutputStream.putNextEntry(zipParameters);

            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }

    void addFilesToArchive(ZipOutputStream zipOutputStream) throws IOException {
        List<String> filesNames = new ArrayList<>();
        filesNames.add("first-file.txt");
        filesNames.add("second-file.txt");

        for (String fileName : filesNames) {
            File file = new File(ZipArchiveController.class.getClassLoader()
              .getResource(fileName).getFile());
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }
}
