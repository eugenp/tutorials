#!/usr/bin/env bash
set -e

psql -U postgres -d postgres -c "DROP DATABASE IF EXISTS flywaydemo"
psql -U postgres -d postgres -c "DROP ROLE IF EXISTS flywaydemo"
