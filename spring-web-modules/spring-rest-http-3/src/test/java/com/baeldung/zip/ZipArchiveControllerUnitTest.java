package com.baeldung.zip;

import net.lingala.zip4j.model.LocalFileHeader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ZipArchiveController.class)
public class ZipArchiveControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenZipArchiveController_whenGetZipArchiveBytes_thenExpectedArchiveShouldContainExpectedFiles() throws Exception {
        MvcResult result = mockMvc.perform(get("/zip-archive"))
          .andReturn();

        MockHttpServletResponse response = result.getResponse();

        byte[] content = response.getContentAsByteArray();

        List<String> fileNames = fetchFileNamesFromArchive(content);
        assertThat(fileNames)
          .containsExactly("first-file.txt", "second-file.txt");
    }

    @Test
    void givenZipArchiveController_whenGetZipArchiveStream_thenExpectedArchiveShouldContainExpectedFiles() throws Exception {
        MvcResult result = mockMvc.perform(get("/zip-archive-stream"))
          .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Thread.sleep(1000);

        byte[] content = response.getContentAsByteArray();

        List<String> fileNames = fetchFileNamesFromArchive(content);
        assertThat(fileNames)
          .containsExactly("first-file.txt", "second-file.txt");
    }

    @Test
    void givenZipArchiveController_whenGetZipArchiveSecuredStream_thenExpectedArchiveShouldContainExpectedFilesSecuredByPassword() throws Exception {
        MvcResult result = mockMvc.perform(get("/zip-archive-stream-secured"))
          .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Thread.sleep(1000);
        byte[] content = response.getContentAsByteArray();

        List<String> fileNames = fetchFileNamesFromArchive(content);
        assertThat(fileNames)
          .containsExactly("first-file.txt", "second-file.txt");
    }

    List<String> fetchFileNamesFromArchive(byte[] content) throws IOException {
        InputStream byteStream = new ByteArrayInputStream(content);
        net.lingala.zip4j.io.inputstream.ZipInputStream zipStream =
          new net.lingala.zip4j.io.inputstream.ZipInputStream(byteStream, "password".toCharArray());

        List<String> fileNames = new ArrayList<>();
        LocalFileHeader entry = zipStream.getNextEntry();
        while (entry != null) {
            fileNames.add(entry.getFileName());
            entry = zipStream.getNextEntry();
        }

        zipStream.close();

        return fileNames;
    }
}
