#!/bin/bash

# Initialize the Kerberos database if it doesn't exist
kdb5_util create -s -P masterpassword

# Run the keytab creation script
kadmin.local -q "addprinc -randkey kafka/localhost@BAELDUNG.COM"
kadmin.local -q "addprinc -randkey zookeeper/zookeeper.sasl_default@BAELDUNG.COM"
kadmin.local -q "addprinc -randkey client/localhost@BAELDUNG.COM"

kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/kafka.keytab kafka/localhost@BAELDUNG.COM"
kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/zookeeper.keytab zookeeper/zookeeper.sasl_default@BAELDUNG.COM"
kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/client.keytab client/localhost@BAELDUNG.COM"

echo "All keytabs have been created"

# Start KDC and Admin services
krb5kdc
kadmind -nofork