# UserApi

All URIs are relative to *https://petstore.swagger.io/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUser**](UserApi.md#createUser) | **POST** /user | Create user
[**createUsersWithArrayInput**](UserApi.md#createUsersWithArrayInput) | **POST** /user/createWithArray | Creates list of users with given input array
[**createUsersWithListInput**](UserApi.md#createUsersWithListInput) | **POST** /user/createWithList | Creates list of users with given input array
[**deleteUser**](UserApi.md#deleteUser) | **DELETE** /user/{username} | Delete user
[**getUserByName**](UserApi.md#getUserByName) | **GET** /user/{username} | Get user by user name
[**loginUser**](UserApi.md#loginUser) | **GET** /user/login | Logs user into the system
[**logoutUser**](UserApi.md#logoutUser) | **GET** /user/logout | Logs out current logged in user session
[**updateUser**](UserApi.md#updateUser) | **PUT** /user/{username} | Updated user



## createUser

> createUser(body)

Create user

This can only be done by the logged in user.

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        User body = new User(); // User | Created user object
        try {
            apiInstance.createUser(body);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#createUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**User**](User.md)| Created user object |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **0** | successful operation |  -  |


## createUsersWithArrayInput

> createUsersWithArrayInput(body)

Creates list of users with given input array

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        List<User> body = Arrays.asList(); // List<User> | List of user object
        try {
            apiInstance.createUsersWithArrayInput(body);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#createUsersWithArrayInput");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;User&gt;**](User.md)| List of user object |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **0** | successful operation |  -  |


## createUsersWithListInput

> createUsersWithListInput(body)

Creates list of users with given input array

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        List<User> body = Arrays.asList(); // List<User> | List of user object
        try {
            apiInstance.createUsersWithListInput(body);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#createUsersWithListInput");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;User&gt;**](User.md)| List of user object |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **0** | successful operation |  -  |


## deleteUser

> deleteUser(username)

Delete user

This can only be done by the logged in user.

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        String username = "username_example"; // String | The name that needs to be deleted
        try {
            apiInstance.deleteUser(username);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#deleteUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**| The name that needs to be deleted |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **400** | Invalid username supplied |  -  |
| **404** | User not found |  -  |


## getUserByName

> User getUserByName(username)

Get user by user name

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        String username = "username_example"; // String | The name that needs to be fetched. Use user1 for testing. 
        try {
            User result = apiInstance.getUserByName(username);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#getUserByName");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**| The name that needs to be fetched. Use user1 for testing.  |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | Invalid username supplied |  -  |
| **404** | User not found |  -  |


## loginUser

> String loginUser(username, password)

Logs user into the system

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        String username = "username_example"; // String | The user name for login
        String password = "password_example"; // String | The password for login in clear text
        try {
            String result = apiInstance.loginUser(username, password);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#loginUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**| The user name for login |
 **password** | **String**| The password for login in clear text |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  * X-Rate-Limit - calls per hour allowed by the user <br>  * X-Expires-After - date in UTC when token expires <br>  |
| **400** | Invalid username/password supplied |  -  |


## logoutUser

> logoutUser()

Logs out current logged in user session

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        try {
            apiInstance.logoutUser();
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#logoutUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **0** | successful operation |  -  |


## updateUser

> updateUser(username, body)

Updated user

This can only be done by the logged in user.

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.UserApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        UserApi apiInstance = new UserApi(defaultClient);
        String username = "username_example"; // String | name that need to be updated
        User body = new User(); // User | Updated user object
        try {
            apiInstance.updateUser(username, body);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#updateUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**| name that need to be updated |
 **body** | [**User**](User.md)| Updated user object |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **400** | Invalid user supplied |  -  |
| **404** | User not found |  -  |

