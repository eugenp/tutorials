# PetApi

All URIs are relative to *https://petstore.swagger.io/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addPet**](PetApi.md#addPet) | **POST** /pet | Add a new pet to the store
[**deletePet**](PetApi.md#deletePet) | **DELETE** /pet/{petId} | Deletes a pet
[**findPetsByStatus**](PetApi.md#findPetsByStatus) | **GET** /pet/findByStatus | Finds Pets by status
[**findPetsByTags**](PetApi.md#findPetsByTags) | **GET** /pet/findByTags | Finds Pets by tags
[**getPetById**](PetApi.md#getPetById) | **GET** /pet/{petId} | Find pet by ID
[**updatePet**](PetApi.md#updatePet) | **PUT** /pet | Update an existing pet
[**updatePetWithForm**](PetApi.md#updatePetWithForm) | **POST** /pet/{petId} | Updates a pet in the store with form data
[**uploadFile**](PetApi.md#uploadFile) | **POST** /pet/{petId}/uploadImage | uploads an image



## addPet

> addPet(body)

Add a new pet to the store

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        Pet body = new Pet(); // Pet | Pet object that needs to be added to the store
        try {
            apiInstance.addPet(body);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#addPet");
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
 **body** | [**Pet**](Pet.md)| Pet object that needs to be added to the store |

### Return type

null (empty response body)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: application/json, application/xml
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **405** | Invalid input |  -  |


## deletePet

> deletePet(petId, apiKey)

Deletes a pet

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        Long petId = 56L; // Long | Pet id to delete
        String apiKey = "apiKey_example"; // String | 
        try {
            apiInstance.deletePet(petId, apiKey);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#deletePet");
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
 **petId** | **Long**| Pet id to delete |
 **apiKey** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **400** | Invalid ID supplied |  -  |
| **404** | Pet not found |  -  |


## findPetsByStatus

> List&lt;Pet&gt; findPetsByStatus(status)

Finds Pets by status

Multiple status values can be provided with comma separated strings

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        List<String> status = Arrays.asList("available"); // List<String> | Status values that need to be considered for filter
        try {
            List<Pet> result = apiInstance.findPetsByStatus(status);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#findPetsByStatus");
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
 **status** | [**List&lt;String&gt;**](String.md)| Status values that need to be considered for filter | [enum: available, pending, sold]

### Return type

[**List&lt;Pet&gt;**](Pet.md)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | Invalid status value |  -  |


## findPetsByTags

> List&lt;Pet&gt; findPetsByTags(tags)

Finds Pets by tags

Multiple tags can be provided with comma separated strings. Use tag1, tag2, tag3 for testing.

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        List<String> tags = Arrays.asList(); // List<String> | Tags to filter by
        try {
            List<Pet> result = apiInstance.findPetsByTags(tags);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#findPetsByTags");
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
 **tags** | [**List&lt;String&gt;**](String.md)| Tags to filter by |

### Return type

[**List&lt;Pet&gt;**](Pet.md)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | Invalid tag value |  -  |


## getPetById

> Pet getPetById(petId)

Find pet by ID

Returns a single pet

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        PetApi apiInstance = new PetApi(defaultClient);
        Long petId = 56L; // Long | ID of pet to return
        try {
            Pet result = apiInstance.getPetById(petId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#getPetById");
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
 **petId** | **Long**| ID of pet to return |

### Return type

[**Pet**](Pet.md)

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | Invalid ID supplied |  -  |
| **404** | Pet not found |  -  |


## updatePet

> updatePet(body)

Update an existing pet

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        Pet body = new Pet(); // Pet | Pet object that needs to be added to the store
        try {
            apiInstance.updatePet(body);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#updatePet");
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
 **body** | [**Pet**](Pet.md)| Pet object that needs to be added to the store |

### Return type

null (empty response body)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: application/json, application/xml
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **400** | Invalid ID supplied |  -  |
| **404** | Pet not found |  -  |
| **405** | Validation exception |  -  |


## updatePetWithForm

> updatePetWithForm(petId, name, status)

Updates a pet in the store with form data

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        Long petId = 56L; // Long | ID of pet that needs to be updated
        String name = "name_example"; // String | Updated name of the pet
        String status = "status_example"; // String | Updated status of the pet
        try {
            apiInstance.updatePetWithForm(petId, name, status);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#updatePetWithForm");
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
 **petId** | **Long**| ID of pet that needs to be updated |
 **name** | **String**| Updated name of the pet | [optional]
 **status** | **String**| Updated status of the pet | [optional]

### Return type

null (empty response body)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: application/x-www-form-urlencoded
- **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **405** | Invalid input |  -  |


## uploadFile

> ModelApiResponse uploadFile(petId, additionalMetadata, file)

uploads an image

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.PetApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi(defaultClient);
        Long petId = 56L; // Long | ID of pet to update
        String additionalMetadata = "additionalMetadata_example"; // String | Additional data to pass to server
        File file = new File("/path/to/file"); // File | file to upload
        try {
            ModelApiResponse result = apiInstance.uploadFile(petId, additionalMetadata, file);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#uploadFile");
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
 **petId** | **Long**| ID of pet to update |
 **additionalMetadata** | **String**| Additional data to pass to server | [optional]
 **file** | **File**| file to upload | [optional]

### Return type

[**ModelApiResponse**](ModelApiResponse.md)

### Authorization

[petstore_auth](../README.md#petstore_auth)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |

