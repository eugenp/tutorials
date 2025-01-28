# Use a minimal Linux base image
FROM debian:bullseye

# Install Kerberos and required utilities
RUN apt-get update && \
    apt-get install -y krb5-kdc krb5-admin-server krb5-user && \
    rm -rf /var/lib/apt/lists/*

# Copy configuration files and scripts
COPY config/krb5.conf /etc/krb5.conf
COPY config/kdc.conf /etc/krb5kdc/kdc.conf
COPY config/kadm5.acl /etc/krb5kdc/kadm5.acl
COPY init_kdc.sh /init_kdc.sh
COPY create_keytabs.sh /create_keytabs.sh

# Set execution permissions
RUN chmod +x /init_kdc.sh /create_keytabs.sh

# Expose KDC ports
EXPOSE 88 749

# Start KDC
CMD ["/init_kdc.sh"]
