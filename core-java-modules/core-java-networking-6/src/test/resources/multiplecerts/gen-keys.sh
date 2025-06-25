#!/bin/bash -e
# @see MultipleCertificatesManualTest
# generates a key for the provided host. overwrites existing files.
# e.g.: gen-keys.sh api.service1 pass123
MYSELF="$(readlink -f $0)"
MYDIR="${MYSELF%/*}"

HOST=${1}; shift
if [[ -z "$HOST" ]]; then
    echo "arg 1 should be the host/alias"
    exit 1
fi

PASSWORD="${1}"; shift
if [[ -z "$PASSWORD" ]]; then
    echo "arg 2 should be the desired password"
    exit 1
fi

KEY_VALIDITY_DAYS=1
CERTS_DIR=$MYDIR/keystore

mkdir -p "$CERTS_DIR"

keytool=keytool

while test $# -gt 0
do
    case "$1" in
        --keytool)
            shift
            keytool="$1"
        ;;
        *)
            echo "unrecognized option: $1"
            exit 1
        ;;
    esac
    shift
done

$keytool -genkeypair\
  -alias $HOST -keyalg RSA -keysize 2048 -storetype PKCS12\
  -keystore "$CERTS_DIR/$HOST.p12" -validity $KEY_VALIDITY_DAYS\
  -dname "CN=$HOST, OU=Dev, O=Baeldung, L=City, ST=State, C=RO"\
  -storepass "$PASSWORD" -keypass "$PASSWORD"

echo "==> generated $HOST keystores in $CERTS_DIR"
