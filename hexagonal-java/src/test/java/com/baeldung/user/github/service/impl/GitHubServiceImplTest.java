package com.baeldung.user.github.service.impl;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.baeldung.user.github.dto.GitHubServiceResponseDTO;
import com.baeldung.user.github.dto.RepositoryDTO;
import com.baeldung.user.github.exception.UserNotFoundException;
import com.baeldung.user.github.service.impl.GitHubServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class GitHubServiceImplTest {

    @InjectMocks
    private GitHubServiceImpl gitHubServiceImpl;

    private ResponseEntity<List<GitHubServiceResponseDTO>> githubRepoDataResponseEntity;
    private ResponseEntity<Map<String, Long>> langaugeResponseEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(gitHubServiceImpl, "token", "ABCD");
        ReflectionTestUtils.setField(gitHubServiceImpl, "gitHubServiceUrl", "https://github.com");

        // Stub GitHubResponse
        GitHubServiceResponseDTO gitHubServiceResponseDTO = new GitHubServiceResponseDTO();
        gitHubServiceResponseDTO.setName("project1");
        gitHubServiceResponseDTO.setFullName("mojombo/project1");
        gitHubServiceResponseDTO.setHtmlUrl("https://github.com/mojombo/project1");
        gitHubServiceResponseDTO.setLogin("mojombo");
        gitHubServiceResponseDTO.setLanguagesUrl("https://github.com/mojombo/project1/languages");
        gitHubServiceResponseDTO.setId(1234L);
        githubRepoDataResponseEntity
                = new ResponseEntity<>(Lists.newArrayList(gitHubServiceResponseDTO),
                HttpStatus.ACCEPTED);

        // Stub GitHub Language response
        Map<String, Long> languages = Maps.newHashMap();
        languages.put("JAVA", 10023L);
        languages.put("XML", 500L);
        langaugeResponseEntity = new ResponseEntity<>(languages,
                HttpStatus.ACCEPTED);
    }

    @Test
    public void testgetRepositoryLanguageInfo() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        // Mock call to GitHub
        Mockito.when(restTemplate.exchange(Mockito.anyString(),
                Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(githubRepoDataResponseEntity)
                .thenReturn(langaugeResponseEntity);

        ReflectionTestUtils.setField(gitHubServiceImpl, "restTemplate", restTemplate);

        List<RepositoryDTO> repositoryDetails = gitHubServiceImpl.getRepositoryLanguageInfo("mojombo");
        Assert.assertNotNull(repositoryDetails);
        Assert.assertFalse(repositoryDetails.isEmpty());
        Mockito.verify(restTemplate);
    }
}
