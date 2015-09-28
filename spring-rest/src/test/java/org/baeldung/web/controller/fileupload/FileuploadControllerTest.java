package org.baeldung.web.controller.fileupload;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class FileuploadControllerTest {

	private static String BASE_URI = "http://localhost:8080/spring-rest";

	@Test
	public void givenConsumingXml_whenReadingTheFoo_thenCorrect() {
		final String URI = BASE_URI + "/upload";
		final String file1 = "fileupload_test1";
		final String file2 = "fileupload_test2";

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(
				Arrays.asList(new MappingJackson2HttpMessageConverter(), new FormHttpMessageConverter()));

		final LinkedMultiValueMap<String, Object> files = new LinkedMultiValueMap<>();
		files.add("file", new ClassPathResource(file1));
		files.add("file", new ClassPathResource(file2));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		final HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				files, headers);

		final Map response = restTemplate.postForObject(URI, entity, Map.class);

		assertThat(response.get(file1), is(6));
		assertThat(response.get(file2), is(7));
	}

}