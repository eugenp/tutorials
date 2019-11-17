#!/bin/bash

docker image build -t spring-data-mongodb:live-test live-test/

docker run -p 27017:27017 --name spring-data-mongodb-live-test -it spring-data-mongodb:live-test
#wait

mvn clean compile test -P live-all

docker stop spring-data-mongodb-live-test
docker rm spring-data-mongodb-live-test
