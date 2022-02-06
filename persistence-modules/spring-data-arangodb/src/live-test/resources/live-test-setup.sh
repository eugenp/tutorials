#!/bin/bash

docker image build -t spring-data-arangodb:live-test .

docker run -p 8529:8529 -e ARANGO_ROOT_PASSWORD=password --name spring-data-arangodb-live-test spring-data-arangodb:live-test

docker exec spring-data-arangodb-live-test arangosh --server.password password --javascript.execute /setup/arangodb-setup.js