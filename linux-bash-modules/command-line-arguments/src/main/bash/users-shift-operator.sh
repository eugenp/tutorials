#!/bin/bash

i=1;
j=$#;
while [ $i -le $j ]
do
echo "Username - $i: $1";
i=$((i + 1));
shift 1;
done
