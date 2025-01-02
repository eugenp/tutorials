#!/bin/sh
# This script creates new OpenAPI generator project.
mkdir -p target
wget -O target/openapi-generator-cli.jar https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/7.4.0/openapi-generator-cli-7.4.0.jar
java -jar target/openapi-generator-cli.jar meta \
  -o . -n java-camel-client -p com.baeldung.openapi.generators.camelclient
