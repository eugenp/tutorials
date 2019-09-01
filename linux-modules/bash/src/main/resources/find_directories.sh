#!/bin/bash

find . -maxdepth 1 -mindepth 1 -type d -printf '%f\n'

find . -maxdepth 1 -mindepth 1 -type d | while read dir; do
  echo "$dir"
done

find . -maxdepth 1 -type d -exec echo {} \;