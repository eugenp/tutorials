#!/usr/bin/env bash
set -e
docker login -u mogronalol -p $DOCKER_PASSWORD
docker build -t baeldung/mesos-marathon-demo:$BUILD_NUMBER .
docker push baeldung/mesos-marathon-demo:$BUILD_NUMBER
