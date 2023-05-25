#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

java -jar $SCRIPTPATH/target/quarkus-app/quarkus-run.jar