# curl setup

# add a department
curl -X POST http://localhost:8080/departments -H "Content-Type: application/json" -d "{\"name\":\"Computer Science\"}"
echo

# add a student
curl -X POST http://localhost:8080/students -H "Content-Type: application/json" -d '{"name":"John Doe", "department":{"id":1}}'
echo

# find a student
curl -X GET http://localhost:8080/students/1
echo