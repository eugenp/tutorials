export TRUSTSTORE=./mysql-server/certs/truststore.jks
export TRUSTSTORE_PASSWORD=mypassword
export KEYSTORE=./mysql-server/certs/client-cert.jks
export KEYSTORE_PASSWORD=mypassword
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/test_db?sslMode=VERIFY_CA
export SPRING_DATASOURCE_USERNAME=test_user
export SPRING_DATASOURCE_PASSWORD=Password2022

java -Djavax.net.ssl.keyStore=$KEYSTORE \
 -Djavax.net.ssl.keyStorePassword=$KEYSTORE_PASSWORD \
 -Djavax.net.ssl.trustStore=$TRUSTSTORE \
 -Djavax.net.ssl.trustStorePassword=$TRUSTSTORE_PASSWORD \
 -jar ./target/spring-boot-mysql-0.1.0.jar