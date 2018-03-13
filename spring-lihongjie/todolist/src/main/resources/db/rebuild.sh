#!/usr/bin/env bash
mysql -uroot -p -Bse "DROP DATABASE todolist;CREATE DATABASE todolist;source schema.sql;"
