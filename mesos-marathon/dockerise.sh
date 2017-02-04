#!/usr/bin/env bash
set -e
docker login -u mogronalol -p $DOCKER_PASSWORD
docker build -t mogronalol/mesos-marathon-demo:$BUILD_NUMBER .
docker push mogronalol/mesos-marathon-demo:$BUILD_NUMBER
