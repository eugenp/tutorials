#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

docker build -f $SCRIPTPATH/src/main/docker/Dockerfile.jvm -t spring-project:0.1-SNAPSHOT $SCRIPTPATH/.

