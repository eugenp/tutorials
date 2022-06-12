#!/bin/bash

$wrk_home/wrk2 -t1 -c20 -d5m -R5000 -s ./post_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 & sleep 60

$wrk_home/wrk2 -t1 -c20 -d5m -R5000 -s ./get_by_city.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ &
$wrk_home/wrk2 -t1 -c20 -d5m -R5000 -s ./get_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ & sleep 120

$wrk_home/wrk2 -t2 -c10 -d3m -R5000 -s ./get_by_city.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ &
$wrk_home/wrk2 -t2 -c10 -d3m -R5000 -s ./get_zipcode.lua --timeout 2m -H 'Host: localhost' http://localhost:8080 \ &

wait
