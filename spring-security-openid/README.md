## Spring Security OpenID

This module contains articles about OpenID with Spring Security

### Relevant articles

- [Spring Security and OpenID Connect](http://www.baeldung.com/spring-security-openid-connect)

### OpenID Connect with Spring Security

### Run the Project

```
mvn spring-boot:run
```

### Obtain Google App - Client ID, Secret

- You need to get client id and client secret by creating a new project at [Google Developer Console](https://console.developers.google.com/project/_/apiui/credential?pli=1)
- Make sure to add OAuth2 credentials by selecting Add credentials > OAuth 2.0 client ID
- Make sure you set redirect URI to http://localhost:8081/google-login

- Once you have your client id and secret, make sure you add them to the `application.properties` of the project

