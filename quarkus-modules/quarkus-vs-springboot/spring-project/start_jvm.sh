#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

java -jar $SCRIPTPATH/target/spring-project-0.1-SNAPSHOT-exec.jar

