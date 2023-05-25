#!/bin/bash

$wrk_home/wrk -t1 -c5 -d1m  -s ./post_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 & sleep 60

$wrk_home/wrk -t1 -c20 -d5m  -s ./post_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 & sleep 60

$wrk_home/wrk -t1 -c20 -d5m -s ./get_by_city.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ &
$wrk_home/wrk -t1 -c20 -d5m -s ./get_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ & sleep 120

$wrk_home/wrk -t2 -c10 -d3m -s ./get_by_city.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ &
$wrk_home/wrk -t2 -c10 -d3m -s ./get_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ &

wait

