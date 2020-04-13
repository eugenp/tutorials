package com.baeldung.user.github.service.impl;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.baeldung.user.github.dto.GitHubServiceResponseDTO;
import com.baeldung.user.github.dto.RepositoryDTO;
import com.baeldung.user.github.exception.UserNotFoundException;
import com.baeldung.user.github.service.GitHubService;

@Slf4j
@Service
public class GitHubServiceImpl implements GitHubService{
	
	/**
	 * GitHub EndPoint
	 */
    @Value("${github.service.url}")
    private String gitHubServiceUrl;
    
    /**
     * Basic Auth token to invoke GitHub services
     */
    @Value("${github.token}")
    private String token;

    /**
     * Injecting RestTemplate Bean
     */
    @Autowired
    private RestTemplate restTemplate;
    
    private static Logger log = LoggerFactory.getLogger(RestTemplate.class);
    
    public GitHubServiceImpl() {}
    
    public GitHubServiceImpl(String gitHubServiceUrl) {
        this.gitHubServiceUrl = gitHubServiceUrl;
    }

    /**
     * Setting the Authtoken using setHeaders method
     * @param token to pass as part of Header
     * @return as HttpEntity object
     */
    private HttpEntity<String> setHeaders(final String token) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.set("Authorization", "Basic "+token);
    	if(token == null || token.isEmpty()) {
    		return null;
    	}
    	return new HttpEntity<String>(headers);
    }
    
    /**
     * Makes a HTTP call to the given {@code url} parameter
     * @param url the URL to make API call to
     * @return The API response in {@link ResponseEntity} object.
     */
    private <T> ResponseEntity<T> getDataFromGitHub(final String url, final ParameterizedTypeReference<T> typeReference) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                setHeaders(token),
                typeReference);
        return responseEntity;
    }

    /**
	 * A simple implementation of a service using a spring restTemplate.
     * Access the GitHub rest service and return the repos and its languages used .
     */
    @Override
    public List<RepositoryDTO> getRepositoryLanguageInfo(String user){
        try {
            ResponseEntity<List<GitHubServiceResponseDTO>> responseEntity = getDataFromGitHub(gitHubServiceUrl+"/users/"+user+"/repos", new ParameterizedTypeReference<List<GitHubServiceResponseDTO>>(){});
            List<GitHubServiceResponseDTO> repos = responseEntity.getBody();
            Map<String, String> repoLangMap = repos.parallelStream().collect(Collectors.toConcurrentMap(GitHubServiceResponseDTO::getName, GitHubServiceResponseDTO::getLanguagesUrl));

            final List<RepositoryDTO> repositoryDetails = new CopyOnWriteArrayList<>();
            repoLangMap.entrySet().parallelStream().forEach(entry -> {
                ResponseEntity<Map<String, Long>> languageDetailsResponse = getDataFromGitHub(entry.getValue(), new ParameterizedTypeReference<Map<String, Long>>(){});
                Map<String, Long> languageDetails = languageDetailsResponse.getBody();
                RepositoryDTO repositoryDetail = new RepositoryDTO();
                repositoryDetail.setLanguages(new ArrayList<>(languageDetails.keySet()));
                repositoryDetail.setName(entry.getKey());
                repositoryDetails.add(repositoryDetail);
            });

            return repositoryDetails;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Service endpoint not found " +gitHubServiceUrl+" or the user may not exists "+user, e);
            }
            //throw new RuntimeException("Failed to GET url " + gitHubServiceUrl, e);
            throw new UserNotFoundException("User with name "+user+" doesn't exist");
        }
    }
}

