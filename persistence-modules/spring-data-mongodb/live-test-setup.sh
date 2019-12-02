#!/bin/bash

docker image build -t spring-data-mongodb:live-test src/live-test/resources/

docker run -p 27017:27017 --name spring-data-mongodb-live-test spring-data-mongodb:live-test
