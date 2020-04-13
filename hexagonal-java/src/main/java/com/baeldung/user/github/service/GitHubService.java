package com.baeldung.user.github.service;

import java.util.List;
import org.springframework.web.client.HttpClientErrorException;
import com.baeldung.user.github.dto.RepositoryDTO;

/**
 * Interface for Github services
 */
public interface GitHubService {

	/**
	 * Abstract method to get the repository and its language info
	 */
	List<RepositoryDTO> getRepositoryLanguageInfo(String user) throws HttpClientErrorException;
}

