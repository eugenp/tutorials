# Use a minimal base image
FROM debian:bullseye

# Install Kerberos and required utilities
RUN apt-get update && \
    apt-get install -y krb5-kdc krb5-admin-server krb5-user && \
    rm -rf /var/lib/apt/lists/*

# Copy configuration files and setup script
COPY config/krb5.conf /etc/krb5.conf
COPY config/kdc.conf /etc/krb5kdc/kdc.conf
COPY setup_kdc.sh /setup_kdc.sh

# Set execution permissions
RUN chmod +x /setup_kdc.sh

# Expose KDC ports
EXPOSE 88 749

# Start KDC
CMD ["/setup_kdc.sh"]
