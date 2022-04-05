#!/bin/bash

docker image build -t spring-data-mongodb:live-test .

docker run -p 27017:27017 --name spring-data-mongodb-live-test spring-data-mongodb:live-test
