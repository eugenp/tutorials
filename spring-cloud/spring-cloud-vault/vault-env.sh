#!/bin/bash
echo Setting environment variables to access local vault..
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
export VAULT_ADDR=https://localhost:8200
export VAULT_CACERT=$SCRIPTPATH/src/test/vault-config/localhost.cert
export VAULT_TLS_SERVER_NAME=localhost
