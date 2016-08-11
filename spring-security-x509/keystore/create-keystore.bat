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

	# Generate a certificate authority (CA)
	keytool -genkey -alias ca -ext BC=ca:true \
	    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
	    -validity 3650 -dname $(DNAME_CA) \
	    -keystore $(KEYSTORE) -storepass $(PASSWORD)

