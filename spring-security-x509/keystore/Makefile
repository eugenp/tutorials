PASSWORD=changeit
KEYSTORE=keystore.jks
HOSTNAME=localhost
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
TRUSTSTORE=truststore.jks
CLIENTNAME=cid

all:	clean create-keystore add-host create-truststore add-client

create-keystore:
	# Generate a certificate authority (CA)
	keytool -genkey -alias ca \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_CA) \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)

add-host:
	# Generate a host certificate
	keytool -genkey -alias $(HOSTNAME) \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_HOST) \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Generate a host certificate signing request
	keytool -certreq -alias $(HOSTNAME) \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA \
	    -validity 3650 -file "$(HOSTNAME).csr" \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Generate signed certificate with the certificate authority
	keytool -gencert -alias ca \
	    -validity 3650 -sigalg SHA512withRSA \
	    -infile "$(HOSTNAME).csr" -outfile "$(HOSTNAME).crt" -rfc \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Import signed certificate into the keystore
	keytool -import -trustcacerts -alias $(HOSTNAME) \
	    -file "$(HOSTNAME).crt" \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)

create-truststore:
	# Export certificate authority into truststore
	keytool -export -alias ca -file ca.crt \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	keytool -import -trustcacerts -noprompt -alias ca -file ca.crt \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)

add-client:
	# Generate client certificate
	keytool -genkey -alias $(CLIENTNAME) \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_HOST) \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)
	# Generate a host certificate signing request
	keytool -certreq -alias $(CLIENTNAME) \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA \
	    -validity 3650 -file "$(CLIENTNAME).csr" \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)
	# Generate signed certificate with the certificate authority
	keytool -gencert -alias ca \
	    -validity 3650 -sigalg SHA512withRSA \
	    -infile "$(CLIENTNAME).csr" -outfile "$(CLIENTNAME).crt" -rfc \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)
	# Import signed certificate into the truststore
	keytool -import -trustcacerts -alias $(CLIENTNAME) \
	    -file "$(CLIENTNAME).crt" \
	    -keystore $(TRUSTSTORE) -storepass $(PASSWORD)

clean:
	rm -f $(KEYSTORE) *.csr *.crt $(TRUSTSTORE)
