#!/bin/bash

# Initialize the Kerberos database if it doesn't exist
kdb5_util create -s -P masterpassword

# Run the keytab creation script
/create_keytabs.sh

# Start KDC and Admin services
krb5kdc
kadmind -nofork