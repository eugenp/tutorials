## JWT Fun

This tutorial walks you through the various features supported by the [JJWT](https://github.com/jwtk/jjwt) library - a fluent interface Java JWT building and parsing library.

### Build and Run

It's super easy to build and exercise this tutorial.

```
mvn clean install
java -jar target/*.jar
```

That's it!

You can hit the home endpoint with your favorite command-line http client. My favorite is: [httpie](https://github.com/jkbrzt/httpie)

`http localhost:8080`

```
Available commands (assumes httpie - https://github.com/jkbrzt/httpie):

  http http://localhost:8080/
	This usage message
	
  http http://localhost:8080/static-builder
	build JWT from hardcoded claims
	
  http POST http://localhost:8080/dynamic-builder-general claim-1=value-1 ... [claim-n=value-n]
	build JWT from passed in claims (using general claims map)
	
  http POST http://localhost:8080/dynamic-builder-specific claim-1=value-1 ... [claim-n=value-n]
	build JWT from passed in claims (using specific claims methods)
	
  http POST http://localhost:8080/dynamic-builder-compress claim-1=value-1 ... [claim-n=value-n]
	build DEFLATE compressed JWT from passed in claims
	
  http http://localhost:8080/parser?jwt=<jwt>
	Parse passed in JWT
	
  http http://localhost:8080/parser-enforce?jwt=<jwt>
	Parse passed in JWT enforcing the 'iss' registered claim and the 'hasMotorcycle' custom claim
```

The Baeldung post that compliments this repo can be found [here](http://www.baeldung.com/)