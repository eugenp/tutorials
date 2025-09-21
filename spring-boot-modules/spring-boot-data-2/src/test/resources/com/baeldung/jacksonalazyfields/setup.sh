#/bin/bash

curl -X POST http://localhost:8080/departments -H "Content-Type: application/json" -d '{"name":"Computer Science"}'
curl -X POST http://localhost:8080/courses -H "Content-Type: application/json" -d '{"name":"machine learning", "department":{"id":1} }'
curl -X POST http://localhost:8080/courses -H "Content-Type: application/json" -d '{"name":"algorithms", "department":{"id":1} }'
curl -X GET http://localhost:8080/courses/1 | jq
curl -X GET http://localhost:8080/departments/1 | jq

curl -X POST http://localhost:8080/departments/1/course -H "Content-Type: application/json" -d '{"name":"crazy course"}' | jq
