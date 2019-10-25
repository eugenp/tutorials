#!/usr/bin/env bash
echo Decrypting my_value as key my_key
spring decrypt --key my_key
echo
echo You should see: \"my_value\"
echo