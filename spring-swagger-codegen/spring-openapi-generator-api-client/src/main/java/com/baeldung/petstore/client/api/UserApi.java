package com.baeldung.petstore.client.api;

import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-03-15T06:14:01.568992-05:00[America/Chicago]")
@Component("com.baeldung.petstore.client.api.UserApi")
public class UserApi {
    private ApiClient apiClient;

    public UserApi() {
        this(new ApiClient());
    }

    @Autowired
    public UserApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create user
     * This can only be done by the logged in user.
     * <p><b>0</b> - successful operation
     * @param body Created user object (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void createUser(User body) throws RestClientException {
        createUserWithHttpInfo(body);
    }

    /**
     * Create user
     * This can only be done by the logged in user.
     * <p><b>0</b> - successful operation
     * @param body Created user object (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> createUserWithHttpInfo(User body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createUser");
        }

        String path = apiClient.expandPath("/user", Collections.<String, Object>emptyMap());

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = { };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
            "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Creates list of users with given input array
     *
     * <p><b>0</b> - successful operation
     * @param body List of user object (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void createUsersWithArrayInput(List<User> body) throws RestClientException {
        createUsersWithArrayInputWithHttpInfo(body);
    }

    /**
     * Creates list of users with given input array
     *
     * <p><b>0</b> - successful operation
     * @param body List of user object (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> createUsersWithArrayInputWithHttpInfo(List<User> body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createUsersWithArrayInput");
        }

        String path = apiClient.expandPath("/user/createWithArray", Collections.<String, Object>emptyMap());

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = { };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
            "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Creates list of users with given input array
     *
     * <p><b>0</b> - successful operation
     * @param body List of user object (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void createUsersWithListInput(List<User> body) throws RestClientException {
        createUsersWithListInputWithHttpInfo(body);
    }

    /**
     * Creates list of users with given input array
     *
     * <p><b>0</b> - successful operation
     * @param body List of user object (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> createUsersWithListInputWithHttpInfo(List<User> body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createUsersWithListInput");
        }

        String path = apiClient.expandPath("/user/createWithList", Collections.<String, Object>emptyMap());

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = { };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
            "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete user
     * This can only be done by the logged in user.
     * <p><b>400</b> - Invalid username supplied
     * <p><b>404</b> - User not found
     * @param username The name that needs to be deleted (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUser(String username) throws RestClientException {
        deleteUserWithHttpInfo(username);
    }

    /**
     * Delete user
     * This can only be done by the logged in user.
     * <p><b>400</b> - Invalid username supplied
     * <p><b>404</b> - User not found
     * @param username The name that needs to be deleted (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserWithHttpInfo(String username) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'username' is set
        if (username == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'username' when calling deleteUser");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("username", username);
        String path = apiClient.expandPath("/user/{username}", uriVariables);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = { };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get user by user name
     *
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Invalid username supplied
     * <p><b>404</b> - User not found
     * @param username The name that needs to be fetched. Use user1 for testing.  (required)
     * @return User
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public User getUserByName(String username) throws RestClientException {
        return getUserByNameWithHttpInfo(username).getBody();
    }

    /**
     * Get user by user name
     *
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Invalid username supplied
     * <p><b>404</b> - User not found
     * @param username The name that needs to be fetched. Use user1 for testing.  (required)
     * @return ResponseEntity&lt;User&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<User> getUserByNameWithHttpInfo(String username) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'username' is set
        if (username == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'username' when calling getUserByName");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("username", username);
        String path = apiClient.expandPath("/user/{username}", uriVariables);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = {
            "application/json", "application/xml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<User> returnType = new ParameterizedTypeReference<User>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Logs user into the system
     *
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Invalid username/password supplied
     * @param username The user name for login (required)
     * @param password The password for login in clear text (required)
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String loginUser(String username, String password) throws RestClientException {
        return loginUserWithHttpInfo(username, password).getBody();
    }

    /**
     * Logs user into the system
     *
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Invalid username/password supplied
     * @param username The user name for login (required)
     * @param password The password for login in clear text (required)
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> loginUserWithHttpInfo(String username, String password) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'username' is set
        if (username == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'username' when calling loginUser");
        }

        // verify the required parameter 'password' is set
        if (password == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'password' when calling loginUser");
        }

        String path = apiClient.expandPath("/user/login", Collections.<String, Object>emptyMap());

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "password", password));

        final String[] accepts = {
            "application/json", "application/xml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Logs out current logged in user session
     *
     * <p><b>0</b> - successful operation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void logoutUser() throws RestClientException {
        logoutUserWithHttpInfo();
    }

    /**
     * Logs out current logged in user session
     *
     * <p><b>0</b> - successful operation
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> logoutUserWithHttpInfo() throws RestClientException {
        Object postBody = null;

        String path = apiClient.expandPath("/user/logout", Collections.<String, Object>emptyMap());

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = { };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Updated user
     * This can only be done by the logged in user.
     * <p><b>400</b> - Invalid user supplied
     * <p><b>404</b> - User not found
     * @param username name that need to be updated (required)
     * @param body Updated user object (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void updateUser(String username, User body) throws RestClientException {
        updateUserWithHttpInfo(username, body);
    }

    /**
     * Updated user
     * This can only be done by the logged in user.
     * <p><b>400</b> - Invalid user supplied
     * <p><b>404</b> - User not found
     * @param username name that need to be updated (required)
     * @param body Updated user object (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> updateUserWithHttpInfo(String username, User body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'username' is set
        if (username == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'username' when calling updateUser");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateUser");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("username", username);
        String path = apiClient.expandPath("/user/{username}", uriVariables);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap formParams = new LinkedMultiValueMap();

        final String[] accepts = { };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
            "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, cookieParams, formParams, accept, contentType, authNames, returnType);
    }
}
