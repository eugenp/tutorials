#!/bin/sh

generate() {
	file="$1"
	size="$2"
	
	fallocate -l "$size" "$file"
	ls -lah "$file"
}

generate /tmp/small.dat 128K
generate /tmp/large.dat 128M
