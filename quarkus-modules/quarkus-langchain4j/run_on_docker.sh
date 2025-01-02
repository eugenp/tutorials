#!/bin/bash

# Build the project
mvn clean package

docker compose -f docker-compose.yml up --build