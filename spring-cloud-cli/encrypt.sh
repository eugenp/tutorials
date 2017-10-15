#!/usr/bin/env bash
@echo off
mode con: cols=130 lines=60

echo Encrypting my_value as key my_key
spring encrypt my_value --key my_key

echo 
echo You should see something like: c93cb36ce1d09d7d62dffd156ef742faaa56f97f135ebd05e90355f80290ce6b
echo 
echo You can use: \"{cipher}c93cb36ce1d09d7d62dffd156ef742faaa56f97f135ebd05e90355f80290ce6b\" in your configuration files
echo 

pause