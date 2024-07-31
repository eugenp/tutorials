# OAuth Test Setup

In order to test the OAuth-secured gateway configurations, please follow the steps below

## Keycloak setup

1. Clone or download the https://github.com/Baeldung/spring-security-oauth project
2. Replace the file `oauth-rest/oauth-authorization-server/src/main/resources/baeldung-realm.json`
   with the one provider here
3. Go to the oauth-rest/oauth-authorization-server folder and use maven to build the project
4. Run the Keycloack service with `mvn spring-boot:run`
5. Once Keycloak is up and running, go to `http://localhost:8083/auth/admin/master/console/#/realms/baeldung` and
   log in with using `bael-admin/pass` as credentials
6. Create two test users, so that one belongs to the *Golden Customers* group and the other doesn't.

## Quotes backend

Use the provided maven profile:

```
$ mvn spring-boot:run -Pquotes-application
```

## Gateway as Resource Server

Use the provided maven profile:

```
$ mvn spring-boot:run -Pgateway-as-resource-server
```

## Gateway as OAuth 2.0 Client

Use the provided maven profile:

```
$ mvn spring-boot:run -Pgateway-as-oauth-client
```


