mkdir certs
cd ./certs

# Generate new CA certificate ca.pem file.
openssl genrsa 2048 > root-ca-key.pem

openssl req -new -x509 -nodes -days 3600 \
    -subj "/C=SE/ST=STOCKHOLM/L=Test/CN=fake-CA" \
    -key root-ca-key.pem -out root-ca.pem

# Create the server-side certificates
openssl req -newkey rsa:2048 -days 3600 -nodes \
    -subj "/C=SE/ST=STOCKHOLM/L=Test/CN=localhost" \
    -keyout server-key.pem -out server-req.pem
openssl rsa -in server-key.pem -out server-key.pem
openssl x509 -req -in server-req.pem -days 3600 \
    -CA root-ca.pem -CAkey root-ca-key.pem -set_serial 01 -out server-cert.pem

# Create the client-side certificates
openssl req -newkey rsa:2048 -days 3600 -nodes \
    -subj "/C=SE/ST=STOCKHOLM/L=Test/CN=localhost" \
    -keyout client-key.pem -out client-req.pem
openssl rsa -in client-key.pem -out client-key.pem
openssl x509 -req -in client-req.pem -days 3600 \
    -CA root-ca.pem -CAkey root-ca-key.pem -set_serial 01 -out client-cert.pem

# Verify the certificates are correct
openssl verify -CAfile root-ca.pem server-cert.pem client-cert.pem

# Convert pem to jks file
keytool -importcert -alias MySQLCACert.jks -file root-ca.pem \
    -keystore truststore.jks -storepass mypassword

openssl pkcs12 -export -in client-cert.pem -inkey client-key.pem -out certificate.p12 -name "certificate"
keytool -importkeystore -srckeystore certificate.p12 -srcstoretype pkcs12 -destkeystore cert.jks