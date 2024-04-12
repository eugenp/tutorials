# Convert pem to jks file
mkdir certs

keytool -importcert -alias MySQLCACert.jks -file ./data/ca.pem \
    -keystore ./certs/truststore.jks -storepass mypassword
openssl pkcs12 -export -in ./data/client-cert.pem -inkey ./data/client-key.pem -out ./certs/certificate.p12 -name "certificate"
keytool -importkeystore -srckeystore ./certs/certificate.p12 -srcstoretype pkcs12 -destkeystore ./certs/client-cert.jks