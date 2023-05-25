#!/bin/bash
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
pushd $SCRIPTPATH
echo Starting vault server...
vault server -config $SCRIPTPATH/src/test/vault-config/vault-test.hcl
