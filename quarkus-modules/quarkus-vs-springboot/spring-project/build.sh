#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

if [ "$1" = "native" ]; then
    mvn clean package -DskipTests spring-boot:build-image -Pnative -f $SCRIPTPATH/pom.xml
elif [ "$1" = "local-native" ]; then
    mvn clean package -DskipTests  -Plocal-native -f $SCRIPTPATH/pom.xml
else
    mvn clean package -DskipTests spring-boot:build-image -f $SCRIPTPATH/pom.xml
fi