#!/bin/sh

echo `date` $@ >> log.txt;
cat log.txt;
