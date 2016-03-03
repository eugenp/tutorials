=========

## OpenID Connect with Spring Security


### Build the Project
```
mvn clean install
```


### Obtain Google app Client ID, Secret
- You need to get client id and client secret from [Google Developer Console](https://console.developers.google.com/project/_/apiui/credential?pli=1)
- Make sure to add OAuth2 credentials by selecting Add credentials > OAuth 2.0 client ID
- Make sure you set redirect URI to http://localhost:8081/google-login


