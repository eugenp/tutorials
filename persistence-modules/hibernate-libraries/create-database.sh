#!/bin/bash
docker run \
  -p 53306:3306 \
  --name=mysql57-hibernate-types \
  -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
  -v "${PWD}/docker/etc/mysql/conf.d":/etc/mysql/conf.d \
  -v "${PWD}/docker/docker-entrypoint-initdb.d":/docker-entrypoint-initdb.d \
  -d mysql:5.7
