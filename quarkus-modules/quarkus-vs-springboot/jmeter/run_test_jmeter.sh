#!/bin/bash

$Jmeter_home/bin/jmeter -n -t load_test.jmx -l log.csv -e -o ./report