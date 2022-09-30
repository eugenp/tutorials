#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

$SCRIPTPATH/target/spring-project -XX:+FlightRecorder -XX:StartFlightRecording="filename=$SCRIPTPATH/recording.jfr,name=Profiling spring"

