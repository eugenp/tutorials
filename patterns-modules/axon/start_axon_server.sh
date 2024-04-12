docker run \
  -d \
  --name axon_server \
  -p 8024:8024 \
  -p 8124:8124 \
  -e AXONIQ_AXONSERVER_NAME=order_demo \
  axoniq/axonserver