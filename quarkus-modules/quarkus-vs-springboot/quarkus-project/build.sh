#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

mvn quarkus:add-extension -Dextensions=container-image-docker

if [ "$1" = "native" ]; then
    mvn clean package -Pnative -Dquarkus.native.container-build=true -f $SCRIPTPATH/pom.xml &&
    docker build -f $SCRIPTPATH/src/main/docker/Dockerfile.native -t quarkus-project:0.1-SNAPSHOT $SCRIPTPATH/.
elif [ "$1" = "local-native" ]; then
    mvn clean package -DskipTests  -Pnative -f $SCRIPTPATH/pom.xml
else
    mvn clean package -Dquarkus.container-build=true -f $SCRIPTPATH/pom.xml &&
    docker build -f $SCRIPTPATH/src/main/docker/Dockerfile.jvm -t quarkus-project:0.1-SNAPSHOT $SCRIPTPATH/.
fi