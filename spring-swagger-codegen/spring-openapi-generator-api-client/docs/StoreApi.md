# StoreApi

All URIs are relative to *https://petstore.swagger.io/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOrder**](StoreApi.md#deleteOrder) | **DELETE** /store/order/{orderId} | Delete purchase order by ID
[**getInventory**](StoreApi.md#getInventory) | **GET** /store/inventory | Returns pet inventories by status
[**getOrderById**](StoreApi.md#getOrderById) | **GET** /store/order/{orderId} | Find purchase order by ID
[**placeOrder**](StoreApi.md#placeOrder) | **POST** /store/order | Place an order for a pet



## deleteOrder

> deleteOrder(orderId)

Delete purchase order by ID

For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.StoreApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        StoreApi apiInstance = new StoreApi(defaultClient);
        Long orderId = 56L; // Long | ID of the order that needs to be deleted
        try {
            apiInstance.deleteOrder(orderId);
        } catch (ApiException e) {
            System.err.println("Exception when calling StoreApi#deleteOrder");
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
 **orderId** | **Long**| ID of the order that needs to be deleted |

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
| **400** | Invalid ID supplied |  -  |
| **404** | Order not found |  -  |


## getInventory

> Map&lt;String, Integer&gt; getInventory()

Returns pet inventories by status

Returns a map of status codes to quantities

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.StoreApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        StoreApi apiInstance = new StoreApi(defaultClient);
        try {
            Map<String, Integer> result = apiInstance.getInventory();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling StoreApi#getInventory");
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

**Map&lt;String, Integer&gt;**

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |


## getOrderById

> Order getOrderById(orderId)

Find purchase order by ID

For valid response try integer IDs with value &gt;&#x3D; 1 and &lt;&#x3D; 10. Other values will generated exceptions

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.StoreApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        StoreApi apiInstance = new StoreApi(defaultClient);
        Long orderId = 56L; // Long | ID of pet that needs to be fetched
        try {
            Order result = apiInstance.getOrderById(orderId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling StoreApi#getOrderById");
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
 **orderId** | **Long**| ID of pet that needs to be fetched |

### Return type

[**Order**](Order.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | Invalid ID supplied |  -  |
| **404** | Order not found |  -  |


## placeOrder

> Order placeOrder(body)

Place an order for a pet

### Example

```java
// Import classes:
import com.baeldung.petstore.client.invoker.ApiClient;
import com.baeldung.petstore.client.invoker.ApiException;
import com.baeldung.petstore.client.invoker.Configuration;
import com.baeldung.petstore.client.invoker.models.*;
import com.baeldung.petstore.client.api.StoreApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://petstore.swagger.io/v2");

        StoreApi apiInstance = new StoreApi(defaultClient);
        Order body = new Order(); // Order | order placed for purchasing the pet
        try {
            Order result = apiInstance.placeOrder(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling StoreApi#placeOrder");
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
 **body** | [**Order**](Order.md)| order placed for purchasing the pet |

### Return type

[**Order**](Order.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, application/xml

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | Invalid Order |  -  |

