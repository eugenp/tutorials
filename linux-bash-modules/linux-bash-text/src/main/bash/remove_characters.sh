#!/bin/bash

my_var="Hola Mundo"
echo ${my_var}

my_filename="interesting-text-file.txt"
echo ${my_filename:0:21}

echo ${my_filename%.*}

complicated_filename="hello-world.tar.gz"
echo ${complicated_filename%%.*}

echo ${my_filename/.*/}

echo 'interesting-text-file.txt' | sed 's/.txt*//'

echo 'interesting-text-file.txt' | cut -f1 -d"."
echo ${complicated_filename} | cut -f1 -d"."
