#!/usr/bin/env bash
@echo off
mode con: cols=130 lines=60

echo Decrypting my_value as key my_key
spring decrypt --key my_key

echo
echo You should see: \"my_value\"
echo