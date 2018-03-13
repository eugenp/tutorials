#!/usr/bin/env bash
set -e

psql -U postgres -c "CREATE ROLE flywaydemo LOGIN  PASSWORD 'flywaydemo'"
psql -U postgres -d postgres -c "CREATE DATABASE flywaydemo OWNER flywaydemo"
