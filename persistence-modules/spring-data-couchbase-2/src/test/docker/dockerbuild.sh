#!/bin/bash

# setup
set -ex
docker rm couchbase_container -f

# main
docker build -t couchbase_image .

# cleanup
set +ex

# run
docker run -d --name couchbase_container -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 couchbase_image
