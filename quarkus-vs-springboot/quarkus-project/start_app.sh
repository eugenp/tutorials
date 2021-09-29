#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

$SCRIPTPATH/target/quarkus-project-0.1-SNAPSHOT-runner -XX:+FlightRecorder -XX:StartFlightRecording="filename=$SCRIPTPATH/recording.jfr,name=Profiling quarkus"