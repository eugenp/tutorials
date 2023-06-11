docker run \
  -d \
  --name order_projection \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin1234 \
  -e MONGO_INITDB_ROOT_PASSWORD=somepassword \
  mongo