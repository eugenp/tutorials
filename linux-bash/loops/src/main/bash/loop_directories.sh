#!/bin/bash

for dir in */; do
    echo "$dir"
done

for file in *; do
    if [ -d "$file" ]; then
        echo "$file"
    fi
done