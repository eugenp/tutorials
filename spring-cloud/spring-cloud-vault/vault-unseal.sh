#!/bin/bash
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
. $SCRIPTPATH/vault-env.sh

# Please replace the unseal keys below for your own
vault operator unseal OfCseaSZzjTZmrxhfx+5clKobwLGCNiJdAlfixSG9E3o
vault operator unseal iplVLPTHW0n0WL5XuI6QWwyNtWbKTek1SoKcG0gR7vdT
vault operator unseal K0TleK3OYUvWFF+uIDsQuf5a+/gkv1PtZ3O47ornzRoF
