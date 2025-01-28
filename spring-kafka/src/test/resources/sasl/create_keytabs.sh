#!/bin/bash

kadmin.local -q "addprinc -pw password1 kafka/baeldung.com@BAELDUNG.COM"
kadmin.local -q "addprinc -pw password2 zookeeper/zookeeper.sasl_default@BAELDUNG.COM"
kadmin.local -q "addprinc -pw password3 client@BAELDUNG.COM"

echo "Generating keytabs"

kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/kafka.keytab kafka/baeldung.com@BAELDUNG.COM"
kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/zookeeper.keytab zookeeper/zookeeper.sasl_default@BAELDUNG.COM"
kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/client.keytab client@BAELDUNG.COM"

echo "All keytabs have been created"