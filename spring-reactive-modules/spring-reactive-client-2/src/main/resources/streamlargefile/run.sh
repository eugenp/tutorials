#!/bin/bash
MYSELF="$(readlink -f "$0")"
MYDIR="${MYSELF%/*}"

client="${1:-Large}"
url="${2:-http://localhost:8081/large-file}"
download_destination="${3:-/tmp/download.dat}"
xmx="${4:-32m}"

module_dir="$(readlink -f "$MYDIR/../../../..")"

echo "module: $module_dir"
cd $module_dir || exit

echo "packaging..."
mvn clean package dependency:copy-dependencies

echo "GET $url with $client client..."
java -Xmx$xmx -cp target/dependency/*:target/* \
"com.baeldung.streamlargefile.client.${client}FileDownloadWebClient" \
"$url" "$download_destination"
