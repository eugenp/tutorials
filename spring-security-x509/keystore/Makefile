PASSWORD=changeit
KEYSTORE=keystore.jks
HOSTNAME=localhost
CLIENTNAME=cid

# CN = Common Name
# OU = Organization Unit
# O  = Organization Name
# L  = Locality Name
# ST = State Name
# C  = Country (2-letter Country Code)
# E  = Email
DNAME_CA='CN=Baeldung CA,OU=baeldung.com,O=Baeldung,L=SomeCity,ST=SomeState,C=CC'
# For server certificates, the Common Name (CN) must be the hostname
DNAME_HOST='CN=$(HOSTNAME),OU=baeldung.com,O=Baeldung,L=SomeCity,ST=SomeState,C=CC'
DNAME_CLIENT='CN=$(CLIENTNAME),OU=baeldung.com,O=Baeldung,L=SomeCity,ST=SomeState,C=CC'
TRUSTSTORE=truststore.jks

all:	clean create-keystore add-host create-truststore add-client

create-keystore:
	# Generate a certificate authority (CA)
	keytool -genkey -alias ca -ext san=dns:localhost,ip:127.0.0.1 -ext BC=ca:true \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_CA) \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)

add-host:
	# Generate a host certificate
	keytool -genkey -alias $(HOSTNAME) -ext san=dns:localhost,ip:127.0.0.1  \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_HOST) \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Generate a host certificate signing request
	keytool -certreq -alias $(HOSTNAME) -ext san=dns:localhost,ip:127.0.0.1 -ext BC=ca:true \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA \
	    -validity 3650 -file "$(HOSTNAME).csr" \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Generate signed certificate with the certificate authority
	keytool -gencert -alias ca -ext san=dns:localhost,ip:127.0.0.1 \
	    -validity 3650 -sigalg SHA512withRSA \
	    -infile "$(HOSTNAME).csr" -outfile "$(HOSTNAME).crt" -rfc \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Import signed certificate into the keystore
	keytool -import -trustcacerts -alias $(HOSTNAME) -ext san=dns:localhost,ip:127.0.0.1 \
	    -file "$(HOSTNAME).crt" \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)

export-authority:
	# Export certificate authority
	keytool -export -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file ca.crt -rfc \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)


create-truststore: export-authority
	# Import certificate authority into a new truststore
	keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file ca.crt \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)

add-client:
	# Generate client certificate
	keytool -genkey -alias $(CLIENTNAME) -ext san=dns:localhost,ip:127.0.0.1 \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_CLIENT) \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)
	# Generate a host certificate signing request
	keytool -certreq -alias $(CLIENTNAME) -ext san=dns:localhost,ip:127.0.0.1 -ext  BC=ca:true \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA \
	    -validity 3650 -file "$(CLIENTNAME).csr" \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)
	# Generate signed certificate with the certificate authority
	keytool -gencert -alias ca -ext san=dns:localhost,ip:127.0.0.1 \
	    -validity 3650 -sigalg SHA512withRSA \
	    -infile "$(CLIENTNAME).csr" -outfile "$(CLIENTNAME).crt" -rfc \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Import signed certificate into the truststore
	keytool -import -trustcacerts -alias $(CLIENTNAME) -ext san=dns:localhost,ip:127.0.0.1 \
	    -file "$(CLIENTNAME).crt" \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)
	# Export private certificate for importing into a browser
	keytool -importkeystore -srcalias $(CLIENTNAME) -ext san=dns:localhost,ip:127.0.0.1 \
	    -srckeystore $(TRUSTSTORE) -srcstorepass $(PASSWORD) \
	    -destkeystore "$(CLIENTNAME).p12" -deststorepass $(PASSWORD) \
	    -deststoretype PKCS12

clean:
	# Remove generated artifacts
	find . \( -name "$(CLIENTNAME)*" -o -name "$(HOSTNAME)*" -o -name "$(KEYSTORE)" -o -name "$(TRUSTSTORE)" -o -name ca.crt \) -type f -exec rm -f {} \;
