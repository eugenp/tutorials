# Use a minimal base image
FROM debian:bullseye

RUN apt-get update && \
    apt-get install -y krb5-kdc krb5-admin-server krb5-user && \
    rm -rf /var/lib/apt/lists/*

COPY config/krb5.conf /etc/krb5.conf
COPY setup_kdc.sh /setup_kdc.sh

RUN chmod +x /setup_kdc.sh

EXPOSE 88 749

CMD ["/setup_kdc.sh"]