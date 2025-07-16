#!/bin/bash

kdb5_util create -s -P masterpassword

kadmin.local -q "addprinc -randkey kafka/localhost@BAELDUNG.COM"
kadmin.local -q "addprinc -randkey zookeeper/zookeeper.sasl_default@BAELDUNG.COM"
kadmin.local -q "addprinc -randkey client@BAELDUNG.COM"

kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/kafka.keytab kafka/localhost@BAELDUNG.COM"
kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/zookeeper.keytab zookeeper/zookeeper.sasl_default@BAELDUNG.COM"
kadmin.local -q "ktadd -k /etc/krb5kdc/keytabs/client.keytab client@BAELDUNG.COM"

krb5kdc
kadmind -nofork