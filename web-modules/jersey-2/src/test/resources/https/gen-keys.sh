#!/bin/bash -e
# @see JerseyHttpsClientManualTest
# generates a CA, signed client/server keys and trust stores for the provided host.
# e.g.: gen-keys.sh api.service <desired-password>
MYSELF="$(readlink -f $0)"
MYDIR="${MYSELF%/*}"

HOST=${1}
if [[ -z "$HOST" ]]; then
    echo "arg 1 should be the host/alias"
    exit 1
fi

PASSWORD="${2}"
if [[ -z "$PASSWORD" ]]; then
    echo "arg 2 should be the desired password"
    exit 1
fi

VALIDITY_DAYS=1
CERTS_DIR=$MYDIR/keystore

mkdir -p "$CERTS_DIR"

keytool=$JAVA_HOME/bin/keytool
$JAVA_HOME/bin/java -version

echo '1. creating certificate authority (CA)'
openssl genrsa -out $CERTS_DIR/ca.${HOST}.key 2048
openssl req -x509 -new -nodes -key $CERTS_DIR/ca.${HOST}.key -sha256 -days $VALIDITY_DAYS -out $CERTS_DIR/ca.${HOST}.crt -subj "/CN=${HOST}"

echo '2. generating server key and CSR'
openssl genrsa -out $CERTS_DIR/server.${HOST}.key 2048
openssl req -new -key $CERTS_DIR/server.${HOST}.key -out $CERTS_DIR/server.${HOST}.csr -subj "/CN=${HOST}"
openssl x509 -req -in $CERTS_DIR/server.${HOST}.csr -CA $CERTS_DIR/ca.${HOST}.crt -CAkey $CERTS_DIR/ca.${HOST}.key -CAcreateserial -out $CERTS_DIR/server.${HOST}.crt -days $VALIDITY_DAYS -sha256

echo '3. generating client key and CSR'
openssl genrsa -out $CERTS_DIR/client.${HOST}.key 2048
openssl req -new -key $CERTS_DIR/client.${HOST}.key -out $CERTS_DIR/client.${HOST}.csr -subj "/CN=${HOST}"
openssl x509 -req -in $CERTS_DIR/client.${HOST}.csr -CA $CERTS_DIR/ca.${HOST}.crt -CAkey $CERTS_DIR/ca.${HOST}.key -CAcreateserial -out $CERTS_DIR/client.${HOST}.crt -days $VALIDITY_DAYS -sha256

echo '4. creating PKCS12 keystores'
openssl pkcs12 -export -out $CERTS_DIR/server.${HOST}.p12 -inkey $CERTS_DIR/server.${HOST}.key -in $CERTS_DIR/server.${HOST}.crt -certfile $CERTS_DIR/ca.${HOST}.crt -name ${HOST} -passout pass:$PASSWORD
openssl pkcs12 -export -out $CERTS_DIR/client.${HOST}.p12 -inkey $CERTS_DIR/client.${HOST}.key -in $CERTS_DIR/client.${HOST}.crt -certfile $CERTS_DIR/ca.${HOST}.crt -name ${HOST} -passout pass:$PASSWORD

echo '5. creating truststore'
$keytool -importcert -keystore $CERTS_DIR/trust.${HOST}.p12 -storetype PKCS12 -storepass $PASSWORD -alias ca.${HOST} -file $CERTS_DIR/ca.${HOST}.crt -noprompt

echo done
