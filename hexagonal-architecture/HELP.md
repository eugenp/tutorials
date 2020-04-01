# Getting Started



Here is a simple example of Hexagonal Architecture in SpringBoot.



Once started you can use following curl request



Add a booking to customer with ID 1

curl --header "Content-Type: application/json" --request PUT --data '{"flights":[{"date": "05MAR","destination": "CDG","number": "7701","origin": "NCE"},{"date": "05MAR","destination": "LAX","number": "006","origin": "CDG"}]}' http://localhost:8080/customer/1/booking



List all bookings of customer 1

curl http://localhost:8080/customer/1/booking



Delete a specific booking of customer 1

curl --request DELETE "http://localhost:8080/customer/1/booking/<any_UUID>"

