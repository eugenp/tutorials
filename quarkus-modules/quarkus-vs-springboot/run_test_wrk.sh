#!/bin/bash

$wrk_home/wrk -t250 -c250 -d5m -s ./post_zipcode.lua --timeout 5s -H 'Host: localhost' http://localhost:8080 & sleep 60

$wrk_home/wrk -t250 -c250 -d5m -s ./get_by_city.lua --timeout 5s -H 'Host: localhost' http://localhost:8080 \ &
$wrk_home/wrk -t250 -c250 -d5m -s ./get_zipcode.lua --timeout 5s -H 'Host: localhost' http://localhost:8080  & sleep 120

$wrk_home/wrk -t250 -c250 -d3m -s ./get_by_city.lua --timeout 5s -H 'Host: localhost' http://localhost:8080 \ & 
$wrk_home/wrk -t250 -c250 -d3m -s ./get_zipcode.lua --timeout 5s -H 'Host: localhost' http://localhost:8080 \ & 

wait