curl -X POST http://localhost:8080/api/ticket-booking ^
  -H "Content-Type: application/json" ^
  -d "{\"movieId\": 1, \"seat\": \"A1\"}"