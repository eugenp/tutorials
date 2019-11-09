## Spring Swagger Codegen API Client

This module contains the code for [Generate Spring Boot REST Client with Swagger](http://www.baeldung.com/spring-boot-rest-client-swagger-codegen).

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.baeldung</groupId>
    <artifactId>spring-swagger-codegen-api-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.baeldung:spring-swagger-codegen-api-client:0.0.1-SNAPSHOT"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/spring-swagger-codegen-api-client-0.0.1-SNAPSHOT.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import com.baeldung.petstore.client.invoker.*;
import com.baeldung.petstore.client.invoker.auth.*;
import com.baeldung.petstore.client.model.*;
import com.baeldung.petstore.client.api.PetApi;

import java.io.File;
import java.util.*;

public class PetApiExample {

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // Configure OAuth2 access token for authorization: petstore_auth
        OAuth petstore_auth = (OAuth) defaultClient.getAuthentication("petstore_auth");
        petstore_auth.setAccessToken("YOUR ACCESS TOKEN");

        PetApi apiInstance = new PetApi();
        Pet body = new Pet(); // Pet | Pet object that needs to be added to the store
        try {
            apiInstance.addPet(body);
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#addPet");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://petstore.swagger.io/v2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*PetApi* | [**addPet**](docs/PetApi.md#addPet) | **POST** /pet | Add a new pet to the store
*PetApi* | [**deletePet**](docs/PetApi.md#deletePet) | **DELETE** /pet/{petId} | Deletes a pet
*PetApi* | [**findPetsByStatus**](docs/PetApi.md#findPetsByStatus) | **GET** /pet/findByStatus | Finds Pets by status
*PetApi* | [**findPetsByTags**](docs/PetApi.md#findPetsByTags) | **GET** /pet/findByTags | Finds Pets by tags
*PetApi* | [**getPetById**](docs/PetApi.md#getPetById) | **GET** /pet/{petId} | Find pet by ID
*PetApi* | [**updatePet**](docs/PetApi.md#updatePet) | **PUT** /pet | Update an existing pet
*PetApi* | [**updatePetWithForm**](docs/PetApi.md#updatePetWithForm) | **POST** /pet/{petId} | Updates a pet in the store with form data
*PetApi* | [**uploadFile**](docs/PetApi.md#uploadFile) | **POST** /pet/{petId}/uploadImage | uploads an image
*StoreApi* | [**deleteOrder**](docs/StoreApi.md#deleteOrder) | **DELETE** /store/order/{orderId} | Delete purchase order by ID
*StoreApi* | [**getInventory**](docs/StoreApi.md#getInventory) | **GET** /store/inventory | Returns pet inventories by status
*StoreApi* | [**getOrderById**](docs/StoreApi.md#getOrderById) | **GET** /store/order/{orderId} | Find purchase order by ID
*StoreApi* | [**placeOrder**](docs/StoreApi.md#placeOrder) | **POST** /store/order | Place an order for a pet
*UserApi* | [**createUser**](docs/UserApi.md#createUser) | **POST** /user | Create user
*UserApi* | [**createUsersWithArrayInput**](docs/UserApi.md#createUsersWithArrayInput) | **POST** /user/createWithArray | Creates list of users with given input array
*UserApi* | [**createUsersWithListInput**](docs/UserApi.md#createUsersWithListInput) | **POST** /user/createWithList | Creates list of users with given input array
*UserApi* | [**deleteUser**](docs/UserApi.md#deleteUser) | **DELETE** /user/{username} | Delete user
*UserApi* | [**getUserByName**](docs/UserApi.md#getUserByName) | **GET** /user/{username} | Get user by user name
*UserApi* | [**loginUser**](docs/UserApi.md#loginUser) | **GET** /user/login | Logs user into the system
*UserApi* | [**logoutUser**](docs/UserApi.md#logoutUser) | **GET** /user/logout | Logs out current logged in user session
*UserApi* | [**updateUser**](docs/UserApi.md#updateUser) | **PUT** /user/{username} | Updated user


## Documentation for Models

 - [Category](docs/Category.md)
 - [ModelApiResponse](docs/ModelApiResponse.md)
 - [Order](docs/Order.md)
 - [Pet](docs/Pet.md)
 - [Tag](docs/Tag.md)
 - [User](docs/User.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### api_key

- **Type**: API key
- **API key parameter name**: api_key
- **Location**: HTTP header

### petstore_auth

- **Type**: OAuth
- **Flow**: implicit
- **Authorization URL**: http://petstore.swagger.io/oauth/dialog
- **Scopes**: 
  - write:pets: modify pets in your account
  - read:pets: read your pets


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

apiteam@swagger.io



