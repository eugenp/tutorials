package com.baeldung.hexagonalarchitecture.adapter.github;

import com.baeldung.hexagonalarchitecture.domain.Avatar;
import com.baeldung.hexagonalarchitecture.domain.CodeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubRepositoryClient implements CodeRepository {
    private final String githubUrl;
    private final RestTemplate restTemplate;

    GithubRepositoryClient(@Value("${code-repository-uri}") String githubUrl, RestTemplate restTemplate) {
        this.githubUrl = githubUrl;
        this.restTemplate = restTemplate;
    }

    public Avatar getAvatarLink(String userName) {
        String url = String.format(githubUrl, userName);
        ResponseEntity<GithubUserResponse> response = restTemplate.getForEntity(url, GithubUserResponse.class);

        return response
          .getBody()
          .asDomain();
    }
}
